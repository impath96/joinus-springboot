package com.joinus.config;

import java.util.Properties;
import javax.net.ssl.SSLSocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Value("${mail.host}")
    private String host;
    @Value("${mail.port}")
    private Integer port;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;

    @Bean
    public JavaMailSender mailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", Boolean.TRUE.toString());
        properties.setProperty("mail.smtp.starttls.enable", Boolean.TRUE.toString());
        properties.setProperty("mail.smtps.checkserveridentity", Boolean.TRUE.toString());
        properties.setProperty("mail.smtps.ssl.trust", "*");
        properties.setProperty("mail.debug", Boolean.TRUE.toString());
        properties.setProperty("mail.smtp.socketFactory.class", SSLSocketFactory.class.getName());
        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }

}
