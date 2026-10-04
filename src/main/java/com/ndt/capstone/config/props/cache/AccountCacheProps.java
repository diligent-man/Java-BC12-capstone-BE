package com.ndt.capstone.config.props.cache;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "cache.account")
public record AccountCacheProps(
    @DefaultValue("prefix") String prefix
) {
    public String failCountKey() {
        return prefix + ":fail_count:";
    }


    public String lockStatusKey() {
        return prefix + ":lock_status:";
    }


    public String wasLockedKey() {
        return prefix + ":was_locked:";
    }


    public String sessionKey() {
        return prefix + ":session:";
    }
}
