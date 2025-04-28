package com.lmlasmo.shrul.testsetup;

import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lmlasmo.shrul.ShrulApplicationTests;

@Transactional
@AutoConfigureMockMvc
public abstract class AbstractBaseControllerTestSetup extends ShrulApplicationTests{

	@AfterEach
	void tearDown() throws JsonProcessingException, Exception {
		TestTransaction.flagForRollback();
		TestTransaction.end();
	}

}
