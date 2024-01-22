package com.example.yxy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "xyk", ignoreInvalidFields = true)
public class XykControllConfig  extends ControllConfig{

}
