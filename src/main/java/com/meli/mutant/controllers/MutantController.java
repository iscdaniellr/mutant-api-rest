package com.meli.mutant.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meli.mutant.dtos.DnaStats;
import com.meli.mutant.dtos.DnaWrapper;
import com.meli.mutant.services.MutantService;

@RestController
@RequestMapping("/api")
public class MutantController {

	@Autowired
	private MutantService mutantService;
	
	private static final Logger logger = LoggerFactory.getLogger(MutantController.class);
	
	@PostMapping("/mutant")
	public ResponseEntity<String> isMutant(@RequestBody DnaWrapper dna) {
		ResponseEntity<String> response = null;
		try{
			response = mutantService.analyzeDna(dna.getDna())?
							new ResponseEntity<>(HttpStatus.OK):
								new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch(Exception ex) {
			logger.error(ex.getMessage());
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping("/stats")
	public ResponseEntity<DnaStats> getStats() {
		DnaStats stats = null;
		try{
			stats = mutantService.getStats();
			return new ResponseEntity<>(stats, HttpStatus.OK);
		} catch(Exception ex) {
			logger.error(ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}