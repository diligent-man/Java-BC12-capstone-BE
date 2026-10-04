package com.ndt.capstone.config.props;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(value = "file.upload")
public record UploadFileProps(
    Image image
) {
    public record Image(
        @DefaultValue("default_cloth.jpg") String defaultImage,
        @DefaultValue("./data/upload/images") String path,
        @DefaultValue(", ") String imageSeparator
    ) {
    }
}
