package com.example.movielist.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DirectorRepository extends CrudRepository<Director, Long> {
	
	List<Director> findByName(String name);

}