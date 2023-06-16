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
	}
}
