package model;

import algo.Algorithm;
import algo.MyhillNerode;

// Implementing the Strategy Pattern
public class MinimizationDFA {
	private Automaton dfa;
	private Algorithm algorithm;

	public MinimizationDFA(Automaton dfa) {
		this.dfa = dfa;
		algorithm = new MyhillNerode();
	}

	public Automaton getDFA() {
		return dfa;
	}

	public void setDFA(Automaton dfa) {
		this.dfa = dfa;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public Automaton minimize() {
		return algorithm.minimize(dfa);
	}
}
