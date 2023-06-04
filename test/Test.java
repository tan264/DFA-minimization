package test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Automaton;
import model.MinimizationDFA;

public class Test {

	public static void main(String[] args) {
		Set<String> states = new HashSet<>();
		states.add("A");
		states.add("B");
		states.add("C");
		states.add("D");
		states.add("E");
		states.add("F");
		states.add("G");
		states.add("H");

		Set<String> symbols = new HashSet<>();
		symbols.add("0");
		symbols.add("1");

		Set<String> finalStates = new HashSet<>();
		finalStates.add("C");

		Map<String, Map<String, String>> transitions = new HashMap<>();

		Map<String, String> aTransitions = new HashMap<>();
		aTransitions.put("0", "B");
		aTransitions.put("1", "F");
		transitions.put("A", aTransitions);

		Map<String, String> bTransitions = new HashMap<>();
		bTransitions.put("0", "G");
		bTransitions.put("1", "C");
		transitions.put("B", bTransitions);

		Map<String, String> cTransitions = new HashMap<>();
		cTransitions.put("0", "A");
		cTransitions.put("1", "C");
		transitions.put("C", cTransitions);

		Map<String, String> dTransitions = new HashMap<>();
		dTransitions.put("0", "C");
		dTransitions.put("1", "G");
		transitions.put("D", dTransitions);

		Map<String, String> eTransitions = new HashMap<>();
		eTransitions.put("0", "H");
		eTransitions.put("1", "F");
		transitions.put("E", eTransitions);

		Map<String, String> fTransitions = new HashMap<>();
		fTransitions.put("0", "C");
		fTransitions.put("1", "G");
		transitions.put("F", fTransitions);

		Map<String, String> gTransitions = new HashMap<>();
		gTransitions.put("0", "G");
		gTransitions.put("1", "E");
		transitions.put("G", gTransitions);

		Map<String, String> hTransitions = new HashMap<>();
		hTransitions.put("0", "G");
		hTransitions.put("1", "C");
		transitions.put("H", hTransitions);

		Automaton dfa = new Automaton(states, symbols, "A", finalStates, transitions);
		System.out.println(dfa.printStates());
		System.out.println(dfa.printSymbols());
		System.out.println(dfa.printFinalStates());
		System.out.println(dfa.printTransitions());

		System.out.println();

		MinimizationDFA solver = new MinimizationDFA(dfa);
		Automaton minimizedAutomaton = solver.minimize();
		System.out.println(minimizedAutomaton.printStates());
		System.out.println(minimizedAutomaton.printStartState());
		System.out.println(minimizedAutomaton.printFinalStates());
		System.out.println(minimizedAutomaton.printTransitions());
	}

}
