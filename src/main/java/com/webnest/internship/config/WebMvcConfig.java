package com.webnest.internship.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /*
     * 跨域设置
     * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600 * 24)
                .allowedHeaders("*");
    }

    /*
     * 配置上传文件的tomcat虚拟目录
     * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Linux下
//        registry.addResourceHandler("/WebNest/uploadFiles/**").addResourceLocations("file:/usr/local/Springboot/WebNest/uploadFiles/");
        //Windows下
        registry.addResourceHandler("/WebNest/uploadFiles/**").addResourceLocations("file:G:/Workspace/IntelliJ IDEA/uploadFiles/");
    }
}
