package com.example.securecustomerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecurecustomerapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurecustomerapiApplication.class, args);
	}

    // @Bean
    // public CommandLineRunner generatePasswordHash() {
    //     return args -> {
    //         String raw = "password123"; 
    //         String hash = new BCryptPasswordEncoder().encode(raw);
    //         System.out.println("BCrypt hash for password123 = " + hash);
    //     };
    // }
}
