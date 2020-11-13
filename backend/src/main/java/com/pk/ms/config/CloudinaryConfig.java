package com.pk.ms.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.pk.ms.services.user.ImageUploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value(value = "${cloudinaryCloudName}")
    private String group_cloud_name;

    @Value(value = "${cloudinaryApiKey}")
    private String group_api_key;

    @Value(value = "${cloudinaryApiSecret}")
    private String group_api_secret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", group_cloud_name,
                "api_key", group_api_key,
                "api_secret", group_api_secret));
    }

    @Bean
    public ImageUploader imageUtils(Cloudinary cloudinary){
        return new ImageUploader(cloudinary);
    }
}
