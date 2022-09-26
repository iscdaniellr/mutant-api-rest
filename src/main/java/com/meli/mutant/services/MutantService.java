package com.meli.mutant.services;

import com.meli.exceptions.ApplicationException;
import com.meli.mutant.dtos.DnaStats;

public interface MutantService {
	
	 public boolean analyzeDna(String[] dna)  throws ApplicationException;
	 
	 public DnaStats getStats();
	 
}
