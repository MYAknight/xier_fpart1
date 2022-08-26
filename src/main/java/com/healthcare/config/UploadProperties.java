package com.healthcare.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 图片上传的Java配置类，通过SpringBoot自动读取配置文件注入
 */
@Data
@Component
@ConfigurationProperties(prefix = "file.upload")
public class UploadProperties {
    // 图片上传路径
    private String path;
    // 文件上传允许类型
    private List<String> allowTypes;
}