package com.revplay.artist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ArtistServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtistServiceApplication.class, args);
    }
}
