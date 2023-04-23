package com.example.movielist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.movielist.domain.AppUser;
import com.example.movielist.domain.Category;
import com.example.movielist.domain.CategoryRepository;
import com.example.movielist.domain.Director;
import com.example.movielist.domain.DirectorRepository;
import com.example.movielist.domain.Movie;
import com.example.movielist.domain.MovieRepository;
import com.example.movielist.domain.UserRepository;

@SpringBootApplication
public class MovielistApplication {

	private static final Logger log = LoggerFactory.getLogger(MovielistApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MovielistApplication.class, args);
	}

	@Bean
	public CommandLineRunner movieDemo(MovieRepository movieRepository, DirectorRepository directorRepository,
			CategoryRepository categoryRepository, UserRepository urepository) {
		return (args) -> {
			log.info("Save some categories");
			Category category1 = new Category("Action");
			categoryRepository.save(category1);
			Category category2 = new Category("Comedy");
			categoryRepository.save(category2);
			Category category3 = new Category("Drama");
			categoryRepository.save(category3);

			log.info("Save some directors");
			Director director1 = new Director("Terry Jones");
			directorRepository.save(director1);
			Director director2 = new Director("Brian De Palma");
			directorRepository.save(director2);
			Director director3 = new Director("Dennis Villeneuve");
			directorRepository.save(director3);

			log.info("Save a movie");
			movieRepository.save(new Movie("Mission: Impossible", director2, 1996, 8, category1));
			movieRepository.save(new Movie("Life of Brian", director1, 1979, 9, category2));
			movieRepository.save(new Movie("Prisoners", director3, 2013, 8, category3));

			urepository.save(new AppUser("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6",
					"user@bookstore.com", "USER"));
			urepository.save(new AppUser("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C",
					"admin@bookstore.com", "ADMIN"));

			log.info("Fetch all movies");
			for (Movie movie : movieRepository.findAll()) {
				log.info(movie.toString());
			}
		};
	}
}
