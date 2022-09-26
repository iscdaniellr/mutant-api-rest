package com.meli.mutant.dtos;

import java.io.Serializable;

public class DnaWrapper implements Serializable{

	private static final long serialVersionUID = 1L;
	private String[] dna;

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}
	
}
