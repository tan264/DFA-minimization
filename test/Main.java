package test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Automaton;
import model.MinimizationDFA;

public class Main {

	public static void main(String[] args) {
		Set<String> states = new HashSet<>();
		states.add("A");
		states.add("B");
		states.add("C");
		states.add("D");
		states.add("E");
		states.add("F");

		Set<String> symbols = new HashSet<>();
		symbols.add("0");
		symbols.add("1");

		Set<String> finalStates = new HashSet<>();
		finalStates.add("C");
		finalStates.add("D");
		finalStates.add("E");

		Map<String, Map<String, String>> transitions = new HashMap<>();

		Map<String, String> aTransitions = new HashMap<>();
		aTransitions.put("0", "B");
		aTransitions.put("1", "C");
		transitions.put("A", aTransitions);

		Map<String, String> bTransitions = new HashMap<>();
		bTransitions.put("0", "A");
		bTransitions.put("1", "D");
		transitions.put("B", bTransitions);

		Map<String, String> cTransitions = new HashMap<>();
		cTransitions.put("0", "E");
		cTransitions.put("1", "F");
		transitions.put("C", cTransitions);

		Map<String, String> dTransitions = new HashMap<>();
		dTransitions.put("0", "E");
		dTransitions.put("1", "F");
		transitions.put("D", dTransitions);

		Map<String, String> eTransitions = new HashMap<>();
		eTransitions.put("0", "E");
		eTransitions.put("1", "F");
		transitions.put("E", eTransitions);

		Map<String, String> fTransitions = new HashMap<>();
		fTransitions.put("0", "F");
		fTransitions.put("1", "F");
		transitions.put("F", fTransitions);

		Automaton dfa = new Automaton(states, symbols, "A", finalStates, transitions);
		System.out.println(dfa.printStates());
		System.out.println(dfa.printSymbols());
		System.out.println(dfa.printFinalStates());
		System.out.println(dfa.printTransitions());

		System.out.println();
		
		MinimizationDFA solver = new MinimizationDFA(dfa);
		Automaton minimizedAutomaton = solver.minimize();
		System.out.println(minimizedAutomaton.printStartState());
		System.out.println(minimizedAutomaton.printFinalStates());
		System.out.println(minimizedAutomaton.printTransitions());

//		Map<String, Map<String, Status>> table = new HashMap<>();
//		Set<String> vistedStates = new HashSet<>();
//		for (String state1 : dfa.getStates()) {
//			vistedStates.add(state1);
//			Map<String, Status> status = new HashMap<>();
//			for (String state2 : dfa.getStates()) {
//				if (!vistedStates.contains(state2)) {
//					if ((dfa.isFinalState(state1) && !dfa.isFinalState(state2))
//							|| (!dfa.isFinalState(state1) && dfa.isFinalState(state2))) {
//						status.put(state2, Status.MARKED);
//					} else {
//						status.put(state2, Status.UNMARKED);
//					}
//
//				}
//			}
//			if (status.size() > 0) {
//				table.put(state1, status);
//			}
//		}
//		System.out.println(table.toString());
//		boolean done = true;
//		while (done) {
//			done = false;
//			for (String state1 : table.keySet()) {
//				for (String state2 : table.get(state1).keySet()) {
//					if (table.get(state1).get(state2) == Status.UNMARKED && canMark(state1, state2, dfa, table)) {
//						done = true;
//						mark(state1, state2, table);
//					}
//				}
//			}
//		}
//		System.out.println(table.toString());

//		StringBuilder sb = new StringBuilder();
//		Set<String> tstates = new HashSet<>();
//		String tstartState = dfa.getStartState();
//		Set<String> tfinalStates = new HashSet<>();
//		Map<String, Map<String, String>> ttransitions = new HashMap<>();
//		tstates.addAll(dfa.getStates());
//		for (String state1 : dfa.getStates()) {
//			Map<String, String> state1Transtitions = new HashMap<>();
//			state1Transtitions.putAll(dfa.getTransitions().get(state1));
//			if(table.containsKey(state1)) {
//				if (tstates.contains(state1)) {
//					for (String state2 : table.get(state1).keySet()) {
//						if (table.get(state1).get(state2) == Status.UNMARKED) {
//							tstates.remove(state2);
//							sb.append(state2);
//							state1Transtitions.putAll(dfa.getTransitions().get(state2));
//						}
//					}
//					if (!sb.isEmpty()) {
//						tstates.remove(state1);
//						String newState = state1 + sb.toString();
//						tstates.add(newState);
//						if (state1.equals(tstartState)) {
//							tstartState = newState;
//						} else if (dfa.getFinalStates().contains(state1)) {
//							tfinalStates.add(newState);
//						}
//						ttransitions.put(newState, state1Transtitions);
//						sb.setLength(0);
//					} else {
//						ttransitions.put(state1, state1Transtitions);
//					}
//				}
//			} else {
//				ttransitions.put(state1, state1Transtitions);
//			}
//		}
//		for(String state1: tstates) {
//			for(String state2: tstates) {
//				for(String tsymbols: dfa.getSymbols()) {
//					if(state2.contains(ttransitions.get(state1).get(tsymbols))) {
//						ttransitions.get(state1).replace(tsymbols, state2);
//					}
//				}
//			}
//		}

//		System.out.println(tstates);
//		System.out.println(tstartState);
//		System.out.println(tfinalStates);
//		System.out.println(ttransitions);

	}

	private static boolean canMark(String state1, String state2, Automaton dfa,
			Map<String, Map<String, Status>> table) {
		Map<String, Map<String, String>> transitions = dfa.getTransitions();
		for (String inputSymbol : dfa.getSymbols()) {
			String temp1 = transitions.get(state1).get(inputSymbol);
			String temp2 = transitions.get(state2).get(inputSymbol);

			if (table.containsKey(temp1)) {
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

	private static void mark(String state1, String state2, Map<String, Map<String, Status>> table) {
		if (table.containsKey(state1)) {
			table.get(state1).replace(state2, Status.MARKED);
		} else if (table.containsKey(state2)) {
			table.get(state2).replace(state1, Status.MARKED);
		}
	}
}

enum Status {
	MARKED, UNMARKED
}