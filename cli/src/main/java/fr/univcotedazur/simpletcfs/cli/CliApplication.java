package fr.univcotedazur.simpletcfs.cli;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class CliApplication {

    @Value("${tcf.host.baseurl}")
    private String serverHostandPort;

    public static void main(String[] args) {
        SpringApplication.exit(SpringApplication.run(CliApplication.class, args), () -> 0);
    }


    /* Spring docs recommend to use the non-blocking, reactive WebClient which offers efficient support for both sync,
   async and streaming scenarios. RestTemplate will be deprecated in the future versions. -> still, MVP mode here
 */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri(serverHostandPort)
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }

}
