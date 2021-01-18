package io.jzheaux.springsecurity.resolutions;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ResolutionInitializer implements SmartInitializingSingleton {
	private final static String READ_ROLE = "resolution:read";
	private final static String WRITE_ROLE = "resolution:write";
	private final static String ENCRYPTED_PSWD = "{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W";
	private final ResolutionRepository resolutions;
	private final UserRepository users;

	public ResolutionInitializer(ResolutionRepository resolutions, UserRepository users) {
		this.resolutions = resolutions;
		this.users = users;
	}

	@Override
	public void afterSingletonsInstantiated() {
		this.resolutions.save(new Resolution("Read War and Peace", "user"));
		this.resolutions.save(new Resolution("Free Solo the Eiffel Tower", "user"));
		this.resolutions.save(new Resolution("Hang Christmas Lights", "user"));

		User user = new User("user", ENCRYPTED_PSWD);
		user.grantAuthority(READ_ROLE);
		user.grantAuthority(WRITE_ROLE);
		this.users.save(user);

		User hasread = new User("hasread", ENCRYPTED_PSWD);
		hasread.grantAuthority(READ_ROLE);
		this.users.save(hasread);

		User haswrite = new User("haswrite", ENCRYPTED_PSWD);
		haswrite.grantAuthority(WRITE_ROLE);
		this.users.save(haswrite);
	}
}
