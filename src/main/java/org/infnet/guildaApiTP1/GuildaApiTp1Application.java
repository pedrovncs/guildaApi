package org.infnet.guildaApiTP1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GuildaApiTp1Application {

    public static void main(String[] args) {
        SpringApplication.run(GuildaApiTp1Application.class, args);
    }
}
