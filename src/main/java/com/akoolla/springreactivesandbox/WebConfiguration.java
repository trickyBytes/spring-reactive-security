package com.akoolla.springreactivesandbox;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Configuration
public class WebConfiguration {

	Mono<ServerResponse> message(ServerRequest serverRequest) {
		return ServerResponse.ok().body(Mono.just("Hello, World"), String.class);
	}

	@Bean
	RouterFunction<?> routes() {
		return route(GET("/message"), this::message);
	}
}

@Configuration
@EnableWebFluxSecurity
class SecurityConfiguration {

	@Bean
	ReactiveUserDetailsService userDetails() {
		UserDetails user = User.withUsername("user").password("{noop}passw0rd").roles("USER").build();
		UserDetails admin = User.withUsername("admin").password("{noop}passw0rd").roles("USER","ADMIN").build();
		
		return new MapReactiveUserDetailsService(user, admin);
	}
}
