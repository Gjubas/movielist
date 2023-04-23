package com.example.movielist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;
	private String title;
	@Column(name = "publishing year")
	private int year;
	private int rating;

	   
	@ManyToOne
	@JsonIgnoreProperties(value = "movies")
	@JoinColumn(name = "categoryid")
	private Category category;
	
	@ManyToOne
	@JsonIgnoreProperties(value = "movies")
	@JoinColumn(name = "directorid")
	private Director director;

	public Movie() {
	}

	public Movie(String title, Director director, int year, int rating, Category category) {
		super();
		this.title = title;
		this.director = director;
		this.year = year;
		this.rating = rating;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
		if (this.rating > 10) {
			this.rating = 10;
		} else if (this.rating < 1) {
			this.rating = 1;
		}
			
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		if (this.category != null)
			return "Movie [id=" + id + ", title=" + title + ", director=" + director + ", year=" + year + ", rating=" + rating + ", category=" + this.getCategory() + "]";
		else
			return "Movie [id=" + id + ", title=" + title + ", director=" + director + ", year=" + year + ", rating=" + rating + "]";
	}
}