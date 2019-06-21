package com.oodrive.poc.demotestcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cats")
public class CatController {

	private final CatService catService;

	@Autowired
	public CatController(CatService catService) {this.catService = catService;}

	@GetMapping(path = "/{catId}")
	public Cat findById(@PathVariable int catId) {
		return catService.findById(catId)
				.orElseThrow(() -> new NullPointerException("Could not find cat with id " + catId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Cat save(@RequestBody Cat cat) {
		int catId = catService.save(cat);
		cat.setCatId(catId);
		return cat;
	}
}
