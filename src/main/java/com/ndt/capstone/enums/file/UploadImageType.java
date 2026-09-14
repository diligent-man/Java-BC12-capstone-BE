package com.ndt.capstone.enums.file;


import lombok.*;


@Getter
@AllArgsConstructor
public enum UploadImageType {
    AVATAR("avatars"),
    PRODUCT("products"),
    ;

    private final String folder;
}
