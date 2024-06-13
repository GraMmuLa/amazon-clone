package com.example.amazonclone;

import com.example.amazonclone.repos.RefreshableRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = RefreshableRepositoryImpl.class)
public class AmazonCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonCloneApplication.class, args);
	}

}
