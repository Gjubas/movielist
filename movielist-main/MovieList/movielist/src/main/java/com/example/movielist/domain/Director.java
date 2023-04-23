package com.example.movielist.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Director {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long directorid;
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "director")

	private List<Movie> movies;

	public Director() {
	}

	public Director(String name) {
		super();
		this.name = name;
	}

	public Long getDirectorid() {
		return directorid;
	}

	public void setDirectorid(Long directorid) {
		this.directorid = directorid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public String toString() {
		return name;
	}

}