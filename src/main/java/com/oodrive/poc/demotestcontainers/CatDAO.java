package com.oodrive.poc.demotestcontainers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CatDAO {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public CatDAO(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

	public int save(Cat cat) {
		return jdbcTemplate.queryForObject("INSERT INTO cat (name, type) VALUES (?, ?) RETURNING cat_id",
				Integer.class,
				cat.getName(),
				cat.getType());
	}

	public Optional<Cat> findById(int catId) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT cat_id, name, type FROM cat WHERE cat_id = ?",
					(rs, rowNum) -> {
						var cat = new Cat(rs.getString("name"), rs.getString("type"));
						cat.setCatId(rs.getInt("cat_id"));
						return cat;
					},
					catId));
		} catch (EmptyResultDataAccessException ignored) {
			return Optional.empty();
		}
	}
}
