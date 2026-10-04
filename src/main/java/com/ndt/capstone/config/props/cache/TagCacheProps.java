package com.ndt.capstone.config.props.cache;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "cache.tag")
public record TagCacheProps(
    @DefaultValue("tag") String prefix,
    @DefaultValue All all
) {
    public record All(@DefaultValue("900000") long cacheDuration) {
    }


    public String allKey() {
        return prefix + ":all";
    }
}
