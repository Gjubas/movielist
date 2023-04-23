package com.example.movielist.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.movielist.domain.Category;
import com.example.movielist.domain.CategoryRepository;
import com.example.movielist.domain.Director;
import com.example.movielist.domain.DirectorRepository;
import com.example.movielist.domain.Movie;
import com.example.movielist.domain.MovieRepository;

@CrossOrigin
@Controller
public class MovielistController {

	@GetMapping("/index")
	public String index() {
		return "welcome";
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private DirectorRepository directorRepository;

	@RequestMapping(value = "/movielist")
	public String movielist(Model model) {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		model.addAttribute("name", username);
		model.addAttribute("movies", movieRepository.findAll());
		return "movielist";
	}

	@GetMapping("/movies")
	public @ResponseBody List<Movie> getMovies() {
		return (List<Movie>) movieRepository.findAll();
	}

	@GetMapping("/movies/{id}")
	public @ResponseBody Optional<Movie> getOneMovie(@PathVariable("id") Long movieId) {
		return movieRepository.findById(movieId);
	}

	@PostMapping("/movies")
	public @ResponseBody Movie addNewMovie(@RequestBody Movie movie) {
		return movieRepository.save(movie);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addMovie(Model model) {
		model.addAttribute("movie", new Movie());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("directors", directorRepository.findAll());
		return "addmovie";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Movie movie) {
		movieRepository.save(movie);
		return "redirect:movielist";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteMovie(@PathVariable("id") Long movieId, Model model) {
		movieRepository.deleteById(movieId);
		return "redirect:../movielist";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editMovie(@PathVariable("id") Long movieId, Model model) {
		model.addAttribute("movie", movieRepository.findById(movieId));
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("directors", directorRepository.findAll());
		return "editmovie";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Movie movie) {
		movieRepository.save(movie);
		return "redirect:movielist";
	}

	// Category

	@RequestMapping(value = { "/categorylist" })
	public String categorylist(Model model) {
		model.addAttribute("categories", categoryRepository.findAll());
		return "categorylist";
	}

	@RequestMapping(value = "/savecategory", method = RequestMethod.POST)
	public String saveCategory(Category category, Model model) {
		categoryRepository.save(category);
		return "redirect:/categorylist";
	}

	@RequestMapping(value = "/addcategory", method = RequestMethod.GET)
	public String addCategory(Model model) {
		model.addAttribute("category", new Category());
		return "addcategory";
	}

	@RequestMapping(value = "/deletecategory/{categoryid}", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable("categoryid") Long categoryid, Model model) {
		model.addAttribute("categories", categoryRepository.findById(categoryid).get().getMovies());
		try {
			categoryRepository.deleteById(categoryid);
			return "redirect:/categorylist";
		} catch (RuntimeException ex) {
			return "deleteerror";
		}
	}

	@RequestMapping(value = "/editcategory/{id}", method = RequestMethod.GET)
	public String editCategory(@PathVariable("id") Long categoryid, Model model) {
		model.addAttribute("category", categoryRepository.findById(categoryid));
		return "editcategory";
	}

	// Director

	@RequestMapping(value = { "directorlist" })
	public String directorlist(Model model) {
		model.addAttribute("directors", directorRepository.findAll());
		return "directorlist";
	}

	@RequestMapping(value = "/savedirector", method = RequestMethod.POST)
	public String saveDirector(Director director, Model model) {
		directorRepository.save(director);
		return "redirect:/directorlist";
	}

	@RequestMapping(value = "/adddirector", method = RequestMethod.GET)
	public String addDirector(Model model) {
		model.addAttribute("director", new Director());
		return "adddirector";
	}

	@RequestMapping(value = "/deletedirector/{directorid}", method = RequestMethod.GET)
	public String deleteDirector(@PathVariable("directorid") Long directorid, Model model) {
		model.addAttribute("directors", directorRepository.findById(directorid).get().getMovies());
		try {
			directorRepository.deleteById(directorid);
			return "redirect:/directorlist";
		} catch (RuntimeException ex) {
			return "deleteerror";
		}
	}

	@RequestMapping(value = "/editdirector/{id}", method = RequestMethod.GET)
	public String editDirector(@PathVariable("id") Long directorid, Model model) {
		model.addAttribute("director", directorRepository.findById(directorid));
		return "editdirector";
	}
}