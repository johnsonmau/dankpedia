package com.johnson.cannabis.controllers;

import com.johnson.cannabis.models.Email;
import com.johnson.cannabis.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class EmailRestController {

    Logger logger = LoggerFactory.getLogger(EmailRestController.class);

    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String emailTo;

    @PostMapping(value = "/support/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> sendEmail(@RequestBody Email email) {

        System.out.print(email.getEmail());

        emailService.sendSimpleMessage(emailTo, "DANKPEDIA CONTACT FROM: " +email.getName() + " | "+ email.getEmail(), email.getComments());

        return ResponseEntity.ok("");
    }


}
