package com.ndt.capstone.service;

import com.ndt.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String FAIL_COUNT_PREFIX = "login:fail_count:";
    private static final String LOCKED_PREFIX = "login:locked:";
    private static final String WAS_LOCKED_PREFIX = "login:was_locked:";
    private static final String SESSION_PREFIX = "login:session:";

    private static final int MAX_ATTEMPTS = 3;
    private static final long LOCK_DURATION_MINUTES = 15;
    private static final long WAS_LOCKED_DURATION_MINUTES = 30;

    @Autowired
    private UserRepository userRepository;

    public String getLockType(String email) {
        return redisTemplate.opsForValue().get(LOCKED_PREFIX + email);
    }

    /**
     * Ghi nhận 1 lần đăng nhập sai.
     * Trả về trạng thái sau khi ghi nhận để AuthServiceImpl quyết định.
     */
    public int recordFailedAttempt(String email) {
        String failKey = FAIL_COUNT_PREFIX + email;
        String lockedKey = LOCKED_PREFIX + email;
        String wasLockedKey = WAS_LOCKED_PREFIX + email;

        // Tăng bộ đếm lên 1
        Long currentAttempts = redisTemplate.opsForValue().increment(failKey);

        // Lần đầu tạo key → set TTL 15 phút cho bộ đếm
        if (currentAttempts != null && currentAttempts == 1) {
            redisTemplate.expire(failKey, LOCK_DURATION_MINUTES, TimeUnit.MINUTES);
        }

        // Đạt ngưỡng 3 lần sai
        if (currentAttempts != null && currentAttempts >= MAX_ATTEMPTS) {

            if (Boolean.TRUE.equals(redisTemplate.hasKey(wasLockedKey))) {
                // ĐÃ từng bị khoá tạm trước đó → KHOÁ VĨNH VIỄN
                redisTemplate.opsForValue().set(lockedKey, "PERMANENT");
                userRepository.findByEmail(email).ifPresent(user -> {
                    user.setStatus("PERMANENTLY_LOCKED");
                    userRepository.save(user);
                });
            } else {
                // CHƯA từng bị khoá → Khoá TẠM 15 phút
                redisTemplate.opsForValue().set(lockedKey, "TEMP",
                        LOCK_DURATION_MINUTES, TimeUnit.MINUTES);
                // Đánh dấu "đã từng bị khoá" (TTL 30 phút)
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

    /**
     * Xóa lịch sử đăng nhập sai (Gọi khi User đăng nhập thành công)
     */
    public void resetFailedAttempts(String email) {
        redisTemplate.delete(FAIL_COUNT_PREFIX + email);
    }

    /**
     * Lưu Session (JWT Token) vào Redis khi đăng nhập thành công
     */
    public void saveSession(String email, String accessToken, long expirationMs) {
        String key = SESSION_PREFIX + email;
        redisTemplate.opsForValue().set(key, accessToken, expirationMs, TimeUnit.MILLISECONDS);
    }

    /**
     * Kiểm tra xem tài khoản có đang được sử dụng ở thiết bị/trình duyệt khác không
     * - Khi LOGIN: if (getActiveSession(email) != null) → "Đang dùng ở nơi khác"
     * - Ở FILTER:  if (!token.equals(getActiveSession(email))) → "Phiên không hợp lệ"
     */
    public String getActiveSession(String email) {
        String key = SESSION_PREFIX + email;
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Đăng xuất (Chủ động xóa session hiện tại)
     */
    public void removeSession(String email) {
        redisTemplate.delete(SESSION_PREFIX + email);
    }

    /**
     * Admin mở khóa tài khoản
     */
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
