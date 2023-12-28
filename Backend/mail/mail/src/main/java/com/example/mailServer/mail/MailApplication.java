package com.example.mailServer.mail;

import com.example.mailServer.mail.services.DataCache.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailApplication {

	public static void main(String[] args) {

		SpringApplication.run(MailApplication.class, args);
	}

}
