package com.energizor.restapi.config;


import com.energizor.restapi.auth.filter.HeaderFilter;
import com.energizor.restapi.auth.interceptor.JwtTokenInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {


    // 정적 자원에 접근을 허용하게 하기 위함
    @Value("${image.add-resource-locations}")
    private String ADD_RESOURCE_LOCATION;

    @Value("${image.add-resource-handler}")
    private String ADD_RESOURCE_HANDLER;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(ADD_RESOURCE_HANDLER)
                .addResourceLocations(ADD_RESOURCE_LOCATION);
    }

    @Bean
    public FilterRegistrationBean<HeaderFilter> getFilterRegistrationBean(){
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>(createHeaderFilter());
        registrationBean.setOrder(Integer.MIN_VALUE);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public HeaderFilter createHeaderFilter(){
        return new HeaderFilter();
    }

    @Bean
    public JwtTokenInterceptor jwtTokenInterceptor(){
        return new JwtTokenInterceptor();
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        // Jackson ObjectMapper 설정
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
//
//        // LocalDateTimeSerializer 등록
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
//        objectMapper.registerModule(javaTimeModule);
//
//        // MappingJackson2HttpMessageConverter 설정
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setObjectMapper(objectMapper);
//        converters.add(converter);
//    }
}
