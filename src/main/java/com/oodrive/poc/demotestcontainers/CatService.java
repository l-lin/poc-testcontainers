package com.oodrive.poc.demotestcontainers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CatService {
	private final CatDAO catDAO;

	@Autowired
	public CatService(CatDAO catDAO) {this.catDAO = catDAO;}

	@Transactional
	public int save(Cat cat) {
		return catDAO.save(cat);
	}

	@Transactional(readOnly = true)
	public Optional<Cat> findById(int catId) {
		return catDAO.findById(catId);
	}
}
