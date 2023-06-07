package test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Automaton;
import model.MinimizationDFA;

public class T {

	public static void main(String[] args) {
		Set<String> states = new HashSet<>();
		states.add("S0");
		states.add("S1");
		states.add("S2");
		states.add("S3");
		states.add("S4");

		Set<String> symbols = new HashSet<>();
		symbols.add("0");
		symbols.add("1");

		Set<String> finalStates = new HashSet<>();
		finalStates.add("S0");

		Map<String, Map<String, String>> transitions = new HashMap<>();

		Map<String, String> s0Transitions = new HashMap<>();
		s0Transitions.put("0", "S1");
		s0Transitions.put("1", "S1");
		transitions.put("S0", s0Transitions);

		Map<String, String> s1Transitions = new HashMap<>();
		s1Transitions.put("0", "S2");
		s1Transitions.put("1", "S3");
		transitions.put("S1", s1Transitions);
		
		Map<String, String> s2Transitions = new HashMap<>();
		s2Transitions.put("0", "S4");
		s2Transitions.put("1", "S0");
		transitions.put("S2", s2Transitions);
		
		Map<String, String> s3Transitions = new HashMap<>();
		s3Transitions.put("0", "S1");
		s3Transitions.put("1", "S2");
		transitions.put("S3", s3Transitions);
		
		Map<String, String> s4Transitions = new HashMap<>();
		s4Transitions.put("0", "S3");
		s4Transitions.put("1", "S4");
		transitions.put("S4", s4Transitions);

		Automaton dfa = new Automaton(states, symbols, "S0", finalStates, transitions);
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
