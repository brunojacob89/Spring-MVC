package br.com.alura.mvc.mudi;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.authorizeHttpRequests((authorize) -> authorize
					.antMatchers("/css/**", "/js/**", "/home/**").permitAll()
					.anyRequest().authenticated()
					)
			.formLogin(form -> form
					.loginPage("/login")
					.defaultSuccessUrl("/usuario/pedido", true).permitAll()
					   )
			.logout(logout -> logout
					.logoutUrl("/logout").logoutSuccessUrl("/home")
						)
			.csrf().disable();
			
			return http.build();
	}
	
	@Autowired
	 public void configure(AuthenticationManagerBuilder auth) throws Exception {
				
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				
//				UserDetails user = User.builder()
//						.username("user")
//						.password(encoder.encode("user"))
//						.roles("USER")
//						.build();
				
				auth.jdbcAuthentication()
					.dataSource(dataSource)
					.passwordEncoder(encoder);
//					.withUser(user);
				
			
	}
	
//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails user = User.withDefaultPasswordEncoder()
//				.username("admin")
//				.password("admin")
//				.roles("ADM")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user);
//	}
	
}
