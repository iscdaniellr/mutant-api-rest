package com.meli.mutant.daos;

import com.meli.entities.DnaAnalyzed;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface DnaDao extends MongoRepository<DnaAnalyzed, String>{
	
	public DnaAnalyzed findByDna(String[] dna);
	
	@Query(value = "{isMutant: ?0}", count = true)
	public long countDna(boolean isMutant);
}
