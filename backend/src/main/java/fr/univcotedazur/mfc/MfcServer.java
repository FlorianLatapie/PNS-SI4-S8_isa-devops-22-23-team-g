package fr.univcotedazur.mfc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MfcServer {

    public static void main(String[] args) {
        SpringApplication.run(MfcServer.class, args);
    }

}
