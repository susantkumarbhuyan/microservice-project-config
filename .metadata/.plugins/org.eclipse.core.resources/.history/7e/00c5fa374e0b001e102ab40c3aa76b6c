package com.hsignz.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
	@Autowired
	private JwtAuthConverter jwtAuthConverter;
	@Autowired
	private CustomPermissionHandler permissionEvaluator;
	public static final String MANAGER = "manager";
	public static final String TEAM_LEAD = "teamlead";
	public static final String SENIOR = "senior";

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.cors(cors -> cors.configurationSource(corsConfigurationSource("http://localhost:8080")));
		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfig -> jwtConfig
				.jwtAuthenticationConverter(jwtAuthConverter).jwkSetUri(SecurityConstants.JWT_JWK_URI)));

		http.exceptionHandling(eh -> eh.authenticationEntryPoint((request, response, authException) -> {
			response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Restricted Content\"");
			response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
		}));

		http.authorizeHttpRequests(
				authorizeHttpRequests -> authorizeHttpRequests.requestMatchers("/demo/api/**", "/preauth/api/**").permitAll()
//				.requestMatchers("/demo/modify", "/demo/view").hasAnyRole(SENIOR, TEAM_LEAD, MANAGER)
//				.requestMatchers("/demo/secure/create").hasAnyRole(TEAM_LEAD, MANAGER)
//				.requestMatchers("/demo/secure/**").hasAnyRole(MANAGER)
						.anyRequest().authenticated());
		return http.build();
	}

	@Bean
	MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
		DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
		handler.setPermissionEvaluator(permissionEvaluator);
		return handler;
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return JwtDecoders.fromIssuerLocation(SecurityConstants.JWT_ISSUER_URI);
	}

	private UrlBasedCorsConfigurationSource corsConfigurationSource(String... origins) {
		final var configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(origins));
		configuration.setAllowedMethods(List.of("*"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setExposedHeaders(List.of("*"));

		final var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
