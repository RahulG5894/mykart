package com.building.mykart.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final Executor executor;

    public EmailService(JavaMailSender mailSender, @Qualifier("customExecutor") Executor executor) {
        this.mailSender = mailSender;
        this.executor = executor;
    }

    public void sendOrderDone(String to) {
        CompletableFuture.runAsync(() -> {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(to);
                message.setSubject("ORDER done");
                message.setText("ORDER done");
                mailSender.send(message);
            } catch (Exception e) {
                log.error("Failed to send mail", e);
                throw new RuntimeException("Failed to send mail", e);
            }
        }, executor);
    }
}
