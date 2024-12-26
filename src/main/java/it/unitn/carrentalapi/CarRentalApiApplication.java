package it.unitn.carrentalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "it.unitn.carrentalapi",
        "it.unitn.carrentalapi.openapi.api"
})
public class CarRentalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalApiApplication.class, args);
    }

}
