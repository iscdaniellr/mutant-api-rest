package com.meli.mutant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.meli.exceptions.InvalidDnaExeption;
import com.meli.mutant.daos.DnaDao;
import com.meli.mutant.services.MutantServiceImp;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MutantServiceTests {
	
	@Mock
	DnaDao dnaDao;
	
	@InjectMocks
	private MutantServiceImp mutantService;

	
	@Test
	void tryAnalyzeDnaMutant() throws Exception {
		String[] dnaMutant = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
		Assertions.assertTrue(mutantService.analyzeDna(dnaMutant));
	}

	@Test
	void tryAnalyzeDnaNoMutant() throws Exception {
		String[] dnaNoMutant = {"TTGCGA","CAGTGC","TTATGT","AGAAGG","CTCCTA","TCACTG"};
		Assertions.assertFalse(mutantService.analyzeDna(dnaNoMutant));
	}
	
	@Test
	void throwInvalidDnaExeptionWrongStructure() {
		String[] invalidDna = {"AGACGAG", "TGCAAGA", "TCATCGG" ,"CGGTAAA" ,"TGAGTGA" ,"TTAACTA" ,"AATGCCCC"};
		Assertions.assertThrows(InvalidDnaExeption.class, ()->mutantService.analyzeDna(invalidDna));
	}
	
	@Test
	void throwInvalidDnaExeptionUnknownNitrogenous() {
		String[] invalidDna = {"AGACGAG", "TGCAAGA", "TCATCGG" ,"CGGTAAA" ,"TGAGTGA" ,"TTAACTA" ,"AATGCCZ"};
		Assertions.assertThrows(InvalidDnaExeption.class, ()->mutantService.analyzeDna(invalidDna));
	}
	
	@Test
	void tryGetStats() {
		Assertions.assertNotNull(mutantService.getStats());
	}
}
