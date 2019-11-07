package com.varma.springflux.springfluxdemo;

import com.varma.springflux.springfluxdemo.model.Product;
import com.varma.springflux.springfluxdemo.repo.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableWebFlux
public class SpringfluxDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringfluxDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ProductRepository  repository){
		return args -> {
			Flux<Product> productFlux = Flux.just(
				new Product(null, "Big Latte", 2.99),
				new Product(null, "Big Decaf", 2.49),
				new Product(null, "Green Tea", 1.99)
			).flatMap(repository::save);

			productFlux.thenMany(repository.findAll()).subscribe(System.out::println);
		};
	}


}
