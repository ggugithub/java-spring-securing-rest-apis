package io.jzheaux.springsecurity.resolutions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class ResolutionsApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(ResolutionsApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests(auth ->
				auth.mvcMatchers("POST", "/resolution/**")
				.hasAuthority("resolution:write")
				.mvcMatchers(HttpMethod.GET, "/resolutions", "/resolution/**")
		.hasAuthority("resolution:read")
		.anyRequest()
		.hasAuthority("resolution:write"))
				.httpBasic(basic -> {});
	}
	/*@Bean
	public UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager(
				org.springframework.security.core.userdetails.User
						.withUsername("user")
						.password("{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W")
						.authorities("resolution:read")
						.build());
	}*/
	@Bean
	public UserDetailsService userDetailsService(UserRepository repository) {
		//return new JdbcUserDetailsManager(dataSource);
		return new UserRepositoryUserDetailsService(repository);
	}

}
