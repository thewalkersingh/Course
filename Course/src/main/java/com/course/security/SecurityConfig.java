package com.course.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
	private final JwtAuthFilter jwtAuthFilter;
	
	public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
		this.jwtAuthFilter = jwtAuthFilter;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		http
			// 1. Disable CSRF (for stateless APIs)
			.csrf(AbstractHttpConfigurer::disable)
			
			// 2. Configure session management
			.sessionManagement(session ->
										 session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			
			// 3. Configure URL-based authorization
			.authorizeHttpRequests(auth -> auth
														 .requestMatchers("/auth/**").permitAll()
														 .requestMatchers("/dashboard/public/**").permitAll()
														 .requestMatchers("/dashboard/user/**").hasRole("USER")
														 .requestMatchers("/dashboard/admin/**").hasRole("ADMIN")
														 // Allow any logged-in user to access everything else
														 .anyRequest().authenticated()
			)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			// 4. Configure authentication mechanism
//			.httpBasic(Customizer.withDefaults())  // Disable Basic Auth for this example
//			.formLogin(Customizer.withDefaults())    // Disable form login for this example
			
			// 5. Configure CORS (if needed)
			.cors(cors -> cors.configure(http));
		
		return http.build();
	}
	
	// Commented all three below as we don't need on jwt
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
	}
	
	
	//	@Bean
	/*public UserDetailsService userDetailsService() {
		UserDetails admin = User.builder()
										.username("admin")
										.password("{noop}admin@1234")
										.roles("ADMIN")
										.build();
		
		UserDetails user = User.builder()
									  .username("user")
									  .password("{noop}admin@1234")
									  .roles("USER")
									  .build();
		UserDetails normal = User.builder()
										 .username("public")
										 .password("{noop}admin@1234")
										 .roles("PUBLIC")
										 .build();
		
		return new InMemoryUserDetailsManager(admin, user, normal);
	}*/
	
}