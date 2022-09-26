package com.meli.mutant.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.meli.constants.NitrogenousBase;
import com.meli.entities.DnaAnalyzed;
import com.meli.exceptions.ApplicationException;
import com.meli.exceptions.InvalidDnaExeption;
import com.meli.mutant.daos.DnaDao;
import com.meli.mutant.dtos.DnaStats;

@Service
public class MutantServiceImp implements MutantService{
	
	@Autowired
	DnaDao dnaDao;

	@Override
	public boolean analyzeDna(String[] dna) throws ApplicationException {
		DnaAnalyzed dnaAnalyzed  = null;
		
		validateDna(dna);
		
		dnaAnalyzed = new DnaAnalyzed();
		dnaAnalyzed.setDna(dna);
		dnaAnalyzed.setMutant(isMutant(dna));

		saveDna(dnaAnalyzed);
		
		return dnaAnalyzed.isMutant();
	}

	@Override
	public DnaStats getStats() {
		DnaStats stats = new DnaStats();
		
		stats.setCount_human_dna(dnaDao.countDna(false));
		stats.setCount_mutant_dna(dnaDao.countDna(true));
		if(stats.getCount_mutant_dna() != 0 && stats.getCount_human_dna() != 0) {
			stats.setRatio((double)stats.getCount_mutant_dna()/(double)stats.getCount_human_dna());
		}
		
		return stats;
	}

	private boolean isMutant(String[] dna) {
		String dnaFragment = null;
		char nitrogenBase;
		int sequences = 0;
		
		for (int i = 0; i < dna.length; i++) {
			dnaFragment = dna[i];
			for (int j = 0; j < dnaFragment.length(); j++) {
				nitrogenBase = dnaFragment.charAt(j);
				if ((j + 3 < dnaFragment.length()) 
						&& detectMutantSequence(nitrogenBase, dnaFragment.charAt(j + 1),dnaFragment.charAt(j + 2), dnaFragment.charAt(j + 3))) {
					sequences ++;
				}
				if ((i + 3 < dna.length) 
						&& detectMutantSequence(nitrogenBase, dna[i + 1].charAt(j), dna[i + 2].charAt(j),dna[i + 3].charAt(j))) {
					sequences ++;
				}
				if ((i + 3 < dna.length && j - 3 > -1) 
						&& detectMutantSequence(nitrogenBase, dna[i + 1].charAt(j - 1),dna[i + 2].charAt(j - 2), dna[i + 3].charAt(j - 3))) {
					sequences ++;
				}
				if ((i + 3 < dna.length && j + 3 < dna.length) 
						&& detectMutantSequence(nitrogenBase, dna[i + 1].charAt(j + 1),dna[i + 2].charAt(j + 2), dna[i + 3].charAt(j + 3))) {
					sequences ++;
				}
				if (sequences > 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean detectMutantSequence(char nitrogenBase1, char nitrogenBase2, char nitrogenBase3, char nitrogenBase4){
        return (nitrogenBase1 == nitrogenBase2 
        		&& nitrogenBase2 == nitrogenBase3 
        		&& nitrogenBase3 == nitrogenBase4);
    }
	
	private void saveDna(DnaAnalyzed dna1) {
		DnaAnalyzed dna = dnaDao.findByDna(dna1.getDna());

		if(dna == null) {
			dna = new DnaAnalyzed(dna1.getDna(), dna1.isMutant());
			dnaDao.save(dna);
		}
	}
	
	private void validateDna(String[] dna) throws ApplicationException{
		for(int i = 0; i < dna.length; i++) {
			if(dna[i].length() != dna.length) {
				throw new InvalidDnaExeption("DNA with wrong structure: String[" + i + "]");
			}
			for(int j = 0; j <dna.length; j++) {
				if(!ObjectUtils.containsConstant(NitrogenousBase.values(), Character. toString(dna[i].charAt(j)), true)){
					throw new InvalidDnaExeption("Unknown nitrogenous base: " + dna[i].charAt(j));
				}
			}
		}
	}
}
