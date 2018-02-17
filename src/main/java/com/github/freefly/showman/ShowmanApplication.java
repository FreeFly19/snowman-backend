package com.github.freefly.showman;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@SpringBootApplication
public class ShowmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShowmanApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(FriendRepo friendRepo) {
		return args -> {
			Friend s = new Friend();
			s.setName("Sven");
			friendRepo.save(s);

			Friend s2 = new Friend();
			s2.setName("Anna");
			friendRepo.save(s2);
		};
	}
}

@RestController
@RequestMapping(value = "api/friends")
class FriendController {
	@Autowired
	FriendRepo friendRepo;

	@GetMapping
	private List<Friend> getFriends() {
		return friendRepo.findAll();
	}

	@PostMapping
	private void createFriend(@RequestBody Friend friend) {
		friendRepo.save(friend);
	}
}

@Repository
interface FriendRepo extends JpaRepository<Friend, Long> {
}

@Data
@Entity
class Friend {
	@Id
	@GeneratedValue
	private Long id;

	private String name;
}