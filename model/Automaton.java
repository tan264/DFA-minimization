package model;

import java.util.Map;
import java.util.Set;

public class Automaton {

	private Set<String> states;
	private Set<String> symbols;
	private String startState;
	private Set<String> finalStates;
	private Map<String, Map<String, String>> transitions;

	public Automaton(Set<String> states, Set<String> symbols, String startState, Set<String> finalStates,
			Map<String, Map<String, String>> transitions) {
		super();
		this.states = states;
		this.symbols = symbols;
		this.startState = startState;
		this.finalStates = finalStates;
		this.transitions = transitions;
	}

	public Boolean isFinalState(String state) {
		return finalStates.contains(state);
	}

	public Map<String, Map<String, String>> getTransitions() {
		return transitions;
	}

	public Set<String> getSymbols() {
		return symbols;
	}

	public Set<String> getFinalStates() {
		return finalStates;
	}

	public String getStartState() {
		return startState;
	}

	public void setStartState(String startState) {
		this.startState = startState;
	}

	public Set<String> getStates() {
		return states;
	}

	public String printStates() {
		StringBuilder sb = new StringBuilder();
		sb.append("States: ");
		for (String i : states) {
			sb.append("\'" + i + "\',");
		}

		return sb.deleteCharAt(sb.length() - 1).toString();
	}

	public String printFinalStates() {
		StringBuilder sb = new StringBuilder();
		sb.append("Final states: ");
		for (String i : finalStates) {
			sb.append("\'" + i + "\',");
		}

		return sb.deleteCharAt(sb.length() - 1).toString();
	}

	public String printSymbols() {
		StringBuilder sb = new StringBuilder();
		sb.append("Symbols: ");
		for (String i : symbols) {
			sb.append("\'" + i + "\',");
		}

		return sb.deleteCharAt(sb.length() - 1).toString();
	}

	public String printTransitions() {
		StringBuilder sb = new StringBuilder();
		for (String state : transitions.keySet()) {
			for (String inputSymbols : transitions.get(state).keySet()) {
				sb.append("(" + state + ", " + inputSymbols + ") -> " + transitions.get(state).get(inputSymbols));
				sb.append("\n");
			}
		}

		return sb.toString();
	}
	
	public String printStartState() {
		return "Start state: '" + startState + "'";
	}
}
