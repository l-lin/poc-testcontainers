package com.oodrive.poc.demotestcontainers;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@ExtendWith(SpringExtension.class)
@Testcontainers
class CatDAOTest {

	private static final String DRIVER = "org.postgresql.Driver";

	private static final String POSTGRES = "postgres";

	@Container
	private PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer)
			new PostgreSQLContainer(PostgreSQLContainer.IMAGE + ":11.2-alpine")
					.withDatabaseName("testcontainers")
					.withUsername(POSTGRES)
					.withPassword(POSTGRES)
					.withClasspathResourceMapping("dump.sql",
							"/docker-entrypoint-initdb.d/dump.sql",
							BindMode.READ_ONLY);

	private CatDAO catDAO;

	@BeforeEach
	void setUp() {
		var ds = DataSourceBuilder.create()
				.url(postgreSQLContainer.getJdbcUrl())
				.driverClassName(DRIVER)
				.username(POSTGRES)
				.password(POSTGRES)
				.build();
		var jdbcTemplate = new JdbcTemplate(ds);
		catDAO = new CatDAO(jdbcTemplate);

		Assertions.assertTrue(postgreSQLContainer.isRunning());
	}

	@Test
	void testDAO() {
		int catId = catDAO.save(new Cat("Grumpy cat", "Tadar Sauce"));
		Assertions.assertEquals(1, catId);

		Optional<Cat> cat = catDAO.findById(catId);
		Assertions.assertTrue(cat.isPresent());
		Assertions.assertEquals("Grumpy cat", cat.get().getName());
		Assertions.assertEquals("Tadar Sauce", cat.get().getType());
	}
}
