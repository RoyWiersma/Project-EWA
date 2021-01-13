package nl.infosupport2.zonneveld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ZonneveldApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZonneveldApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                    .allowedOrigins("https://infosupport-2-front-end.herokuapp.com", "http://localhost:8085",
                            "http://localhost:4200");
            }
        };
    }
}
