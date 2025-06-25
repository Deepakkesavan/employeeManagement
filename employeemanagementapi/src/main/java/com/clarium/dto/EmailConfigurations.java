package com.clarium.dto;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mail-config")
public class EmailConfigurations {

    private String fromAddress;
    private String toAddress;
    private String subject;

    public EmailConfigurations() {
    }

    public EmailConfigurations(String fromAddress, String toAddress, String subject) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.subject = subject;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
