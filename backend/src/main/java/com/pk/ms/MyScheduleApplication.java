package com.pk.ms;

import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MyScheduleApplication {

//	@Autowired
//	UserService userService;
//
//	@Autowired
//	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(MyScheduleApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner() {
//		return args -> {
//			MyScheduleUser user1 = userService.getUserById(1);
//			MyScheduleUser user2 = userService.getUserById(2);
//			MyScheduleUser user3 = userService.getUserById(3);
//
//			user1.setPassword(encoder.encode(user1.getPassword()));
//			userService.save(user1);
//
//			user2.setPassword(encoder.encode(user2.getPassword()));
//			userService.save(user2);
//
//			user3.setPassword(encoder.encode(user3.getPassword()));
//			userService.save(user3);
//		};
//	}
}
