package org.chainoptim.core.user.util.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(Object.class, HibernateProxyMixin.class);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.hibernate.proxy.HibernateProxy;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JacksonConfiguration {
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        // Register Hibernate5Module to handle Hibernate lazy-loading proxies.
//        mapper.registerModule(new Hibernate5Module());
//
//        // Register JavaTimeModule for Java 8 date/time types
//        mapper.registerModule(new JavaTimeModule());
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        // Mix-in for HibernateProxy
//        mapper.addMixIn(HibernateProxy.class, HibernateProxyMixin.class);
//
//        return mapper;
//    }
//
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    private abstract class HibernateProxyMixin {}
//}
