package com.udemy.vinsguru.graphql.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Validated
@Controller
public class GraphqlController {

	@QueryMapping("sayHello")
	public Mono<String> helloWorld() {
		return Mono.just("Hello, World!");
	}

	@QueryMapping
	public Mono<String> sayHelloTo(@Argument @Valid HelloInput input) {
		return Mono.fromSupplier(() -> "Hello " + input.name());
	}

	public record HelloInput(
			@Pattern(regexp = "^[A-Za-z ]{1,15}$", message = "Name must contain 1 to 15 letters and spaces")
			String name
	) {}

}
