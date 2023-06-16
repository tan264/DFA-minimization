package algo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Automaton;

enum Status {
	MARKED, UNMARKED
}

// Implementing the Myhill-Nerode algorithm
public class MyhillNerode implements Algorithm {

	private Map<String, Map<String, Status>> table = new HashMap<>();
//	private Automaton dfa;

	@Override
	public Automaton minimize(Automaton dfa) {
		// Clone the DFA to avoid modifying the original DFA
		cloneDFA(dfa);

		// Step 1
		deleteUnreachableState(dfa);

		// Step 2
		initTable(dfa);
		boolean done = true;
		while (done) {
			done = false;
			for (String state1 : table.keySet()) {
				for (String state2 : table.get(state1).keySet()) {
					if (table.get(state1).get(state2) == Status.UNMARKED && canMark(state1, state2, dfa)) {
						done = true;
						mark(state1, state2);
					}
				}
			}
		}

		// Step 3
		return buildNewAutomaton(dfa);
	}

	private void cloneDFA(Automaton dfa) {
		Set<String> states = new HashSet<>();
		states.addAll(dfa.getStates());

		Set<String> symbols = new HashSet<>();
		symbols.addAll(dfa.getSymbols());

		Set<String> finalStates = new HashSet<>();
		finalStates.addAll(dfa.getFinalStates());

		Map<String, Map<String, String>> transitions = new HashMap<>();
		transitions.putAll(dfa.getTransitions());

		dfa = new Automaton(states, symbols, dfa.getStartState(), finalStates, transitions);
	}

	private void deleteUnreachableState(Automaton dfa) {
		Set<String> reachableStates = new HashSet<>();
		for (String state : dfa.getStates()) {
			for (String inputSymbol : dfa.getSymbols()) {
				reachableStates.add(dfa.getTransitions().get(state).get(inputSymbol));
			}
		}

		Set<String> originalStates = new HashSet<>();
		originalStates.addAll(dfa.getStates());

		for (String state : originalStates) {
			if (!state.equals(dfa.getStartState()) && !reachableStates.contains(state)) {
				dfa.getStates().remove(state);
				dfa.getTransitions().remove(state);
				dfa.getFinalStates().remove(state);
			}
		}
	}

	private void initTable(Automaton dfa) {
		Set<String> vistedStates = new HashSet<>();
		for (String state1 : dfa.getStates()) {
			vistedStates.add(state1);
			Map<String, Status> status = new HashMap<>();
			for (String state2 : dfa.getStates()) {
				if (!vistedStates.contains(state2)) {
					if ((dfa.isFinalState(state1) && !dfa.isFinalState(state2))
							|| (!dfa.isFinalState(state1) && dfa.isFinalState(state2))) {
						status.put(state2, Status.MARKED);
					} else {
						status.put(state2, Status.UNMARKED);
					}

				}
			}
			if (status.size() > 0) {
				table.put(state1, status);
			}
		}
	}

	private boolean canMark(String state1, String state2, Automaton dfa) {
		Map<String, Map<String, String>> transitions = dfa.getTransitions();
		for (String inputSymbol : dfa.getSymbols()) {
			String temp1 = transitions.get(state1).get(inputSymbol);
			String temp2 = transitions.get(state2).get(inputSymbol);

			if (table.containsKey(temp1) && table.containsKey(temp2)) {
				if (table.get(temp1).get(temp2) == Status.MARKED || table.get(temp2).get(temp1) == Status.MARKED) {
					return true;
				}
			} else if (table.containsKey(temp1)) {
				if (table.get(temp1).get(temp2) == Status.MARKED) {
					return true;
				}
			} else if (table.containsKey(temp2)) {
				if (table.get(temp2).get(temp1) == Status.MARKED) {
					return true;
				}
			}
		}

		return false;
	}

	private void mark(String state1, String state2) {
		if (table.containsKey(state1)) {
			table.get(state1).replace(state2, Status.MARKED);
		} else if (table.containsKey(state2)) {
			table.get(state2).replace(state1, Status.MARKED);
		}
	}

	private Automaton buildNewAutomaton(Automaton dfa) {
		StringBuilder sb = new StringBuilder();
		Set<String> states = new HashSet<>();
		String startState = dfa.getStartState();
		Set<String> finalStates = new HashSet<>();
		Map<String, Map<String, String>> transitions = new HashMap<>();
		states.addAll(dfa.getStates());
		for (String state1 : dfa.getStates()) {
			Map<String, String> state1Transtitions = new HashMap<>();
			state1Transtitions.putAll(dfa.getTransitions().get(state1));
			if (table.containsKey(state1)) {
				if (states.contains(state1)) {
					for (String state2 : table.get(state1).keySet()) {
						if (table.get(state1).get(state2) == Status.UNMARKED) {
							states.remove(state2);
							sb.append(state2);
							state1Transtitions.putAll(dfa.getTransitions().get(state2));
						}
					}
					if (!sb.isEmpty()) {
						states.remove(state1);
						String newState = state1 + sb.toString();
						states.add(newState);
						if (state1.equals(startState)) {
							startState = newState;
						} else if (dfa.getFinalStates().contains(state1)) {
							finalStates.add(newState);
						}
						transitions.put(newState, state1Transtitions);
						sb.setLength(0);
					} else {
						transitions.put(state1, state1Transtitions);
						if (dfa.getFinalStates().contains(state1)) {
							finalStates.add(state1);
						}
					}
				}
			} else if (states.contains(state1)) {
				transitions.put(state1, state1Transtitions);
			}
		}
		for (String state1 : states) {
			for (String state2 : states) {
				for (String symbol : dfa.getSymbols()) {
//					original code
//					if (state2.contains(transitions.get(state1).get(symbol))) {
//						transitions.get(state1).replace(symbol, state2);
//					}

					if (transitions.get(state1).containsKey(symbol)
							&& state2.contains(transitions.get(state1).get(symbol))) {
						transitions.get(state1).replace(symbol, state2);
					}
				}
			}
		}

		return new Automaton(states, dfa.getSymbols(), startState, finalStates, transitions);
	}
}
