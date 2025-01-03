package it.unitn.carrentalapi;

import it.unitn.carrentalapi.config.AppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        AppConfiguration.class
})
public class CarRentalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalApiApplication.class, args);
    }

}
