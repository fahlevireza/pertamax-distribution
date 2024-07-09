package com.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest(classes = PertamaxDistributionApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@SpringJUnitConfig
class PertamaxDistributionApplicationTests {

	@Test
	void contextLoads() {
	}

}
