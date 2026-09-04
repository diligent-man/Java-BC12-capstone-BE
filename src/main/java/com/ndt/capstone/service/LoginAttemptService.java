package com.ndt.capstone.service;

import java.util.concurrent.TimeUnit;

import com.ndt.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private final RedisTemplate<String, String> redisTemplate;

    // tao ra các key cần thiết
    // luu số lần sai liên tiếp
    private static final String FAIL_COUNT_PREFIX = "login:fail_count:";

    // luu trạng thái khoa:  temp hay pernament
    private static final String LOCKED_PREFIX = "login:locked:";

    // luu trang thai Khóa: đã từng bị khóa tạm không ?
    private static final String WAS_LOCKED_PREFIX = "login:was_locked:";

    // luu token cua phien hoat động: để xét đk chỉ cho đúng 1 trình duyệt active tại 1 thời điểm
    private static final String SESSION_PREFIX = "login:session:";

    // các hằng số thời gian của key
    private static final int MAX_ATTEMPTS = 3;

    private static final long LOCK_DURATION_MINUTES = 15;

    private static final long WAS_LOCKED_DURATION_MINUTES = 30;

    @Autowired
    private UserRepository userRepository;


    // ============================================================
    // METHOD 1: getLockType
    // Kiểm tra tài khoản có bị khoá không và loại khoá là gì
    // Return: "TEMP", "PERMANENT", hoặc null (không bị khoá)
    // ============================================================
    public String getLockType(String email) {
        String key = LOCKED_PREFIX + email;
        return redisTemplate.opsForValue().get(key);
    }


    // ============================================================
    // METHOD 2: recordFailedAttempt
    // Ghi nhận 1 lần đăng nhập sai, tự động khoá nếu đạt ngưỡng
    // Return: số lần thử CÒN LẠI (2, 1, hoặc 0)
    // ============================================================
    public int recordFailedAttempt(String email) { // hàm này chỉ được kích hoạt khi user nhập sai mật khẩu
        String failKey = FAIL_COUNT_PREFIX + email;
        String lockedKey = LOCKED_PREFIX + email;
        String wasLockedKey = WAS_LOCKED_PREFIX + email;

        // tăng số lần sai lên 1 (thông qua .increment)
        Long currentAttempts = redisTemplate.opsForValue().increment(failKey);

        // khi nhập sai lần 1, lập tức set thời gian cho key fail count là 15p, cho người dùng nếu bỏ đi đâu đó thì sau 15p quay lại sẽ nhận lại đủ 3 lần attempt
        if (currentAttempts != null && currentAttempts == 1) {
            redisTemplate.expire(failKey, LOCK_DURATION_MINUTES, TimeUnit.MINUTES);
        }

        // Nếu đạt ngưỡng 3 lần sai, tạo ra các key khóa login tương ứng
        if (currentAttempts != null && currentAttempts >= MAX_ATTEMPTS) {

            if (Boolean.TRUE.equals(redisTemplate.hasKey(wasLockedKey))) {
                // ĐÃ từng bị khoá tạm trước đó → KHOÁ VĨNH VIỄN
                redisTemplate.opsForValue().set(lockedKey, "PERMANENT");
                // ★ THÊM: Cập nhật trạng thái trong Database
                userRepository.findByEmail(email).ifPresent(user -> {
                    user.setStatus("PERMANENTLY_LOCKED");
                    userRepository.save(user);
                });
            } else {
                // CHƯA từng bị khoá → Khoá TẠM 15 phút, sau 15p này thì vẫn còn key waslocked key = true, nếu trong 15p này mà vẫn nhập sau mk thì se bị khóa vĩnh viển
                redisTemplate.opsForValue().set(lockedKey, "TEMP",
                    LOCK_DURATION_MINUTES, TimeUnit.MINUTES);
                // Đánh dấu "đã từng bị khoá" (TTL 30 phút), sau 15p nữa (vì vừa bị khóa 15p do locked key set = temp) nếu ng dùng bỏ đi đâu đó và quay lại nó được nhận lại đủ 3 lần attempt
                redisTemplate.opsForValue().set(wasLockedKey, "true",
                    WAS_LOCKED_DURATION_MINUTES, TimeUnit.MINUTES);
            }

            // Xoá bộ đếm (vì đã khoá rồi, không cần đếm nữa)
            redisTemplate.delete(failKey);
            return 0;
        }

        // Chưa đạt ngưỡng → trả về số lần còn lại
        return MAX_ATTEMPTS - currentAttempts.intValue();
    }


    // ============================================================
    // METHOD 3: resetFailedAttempts
    // Xoá bộ đếm sai khi đăng nhập thành công
    // ============================================================
    public void resetFailedAttempts(String email) {
        redisTemplate.delete(FAIL_COUNT_PREFIX + email);
    }


    // ============================================================
    // METHOD 4: saveSession
    // Lưu token vào Redis khi đăng nhập thành công (Single Session)
    // expirationMs: thời gian sống của JWT (mili giây)
    // ============================================================
    public void saveSession(String email, String accessToken, long expirationMs) {
        String key = SESSION_PREFIX + email;
        redisTemplate.opsForValue().set(key, accessToken, expirationMs, TimeUnit.MILLISECONDS);
    }


    // ============================================================
    // METHOD 5: getActiveSession
    // Lấy token đang lưu trong Redis
    // Return: token hiện tại, hoặc null (chưa có session)
    //
    // CÁCH DÙNG:
    //   - Khi LOGIN: if (getActiveSession(email) != null) → "Đang dùng ở nơi khác"
    //   - Ở FILTER:  if (!token.equals(getActiveSession(email))) → "Phiên không hợp lệ"
    // ============================================================
    public String getActiveSession(String email) {
        String key = SESSION_PREFIX + email;
        return redisTemplate.opsForValue().get(key);
    }


    // ============================================================
    // METHOD 6: removeSession
    // Xoá session khi user đăng xuất
    // ============================================================
    public void removeSession(String email) {
        redisTemplate.delete(SESSION_PREFIX + email);
    }


    // ============================================================
    // METHOD 7: unlockAccount
    // Admin mở khoá tài khoản — xoá tất cả key liên quan
    // ============================================================
    public void unlockAccount(String email) {
        redisTemplate.delete(LOCKED_PREFIX + email);
        redisTemplate.delete(WAS_LOCKED_PREFIX + email);
        redisTemplate.delete(FAIL_COUNT_PREFIX + email);

        userRepository.findByEmail(email).ifPresent(user -> {
            user.setStatus("ACTIVE");
            userRepository.save(user);
        });
    }

}