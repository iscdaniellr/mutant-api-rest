package com.meli.mutant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import com.meli.mutant.controllers.MutantController;
import com.meli.mutant.dtos.DnaWrapper;
import com.meli.mutant.services.MutantService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MutantApiRestApplicationTests {
	
	@Mock
	MutantService mutantService;
	
	@InjectMocks
	private MutantController mutantController;


	@Test
	void checkAnswerIsMutant() throws Exception {
		String[] dnaMutant = {"AAAA","AAAAC","AAAA","AAAA"};
		DnaWrapper dnaWrapper = new DnaWrapper();
		dnaWrapper.setDna(dnaMutant);

		Assertions.assertNotNull(mutantController.isMutant(dnaWrapper));
	}

	@Test
	void checkAnswerGetStats() throws Exception {
		Assertions.assertNotNull(mutantController.getStats());
	}
	
}
