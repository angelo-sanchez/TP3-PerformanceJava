package edu.isistan;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.isistan.IProblemSolver.Pair;
import edu.isistan.solutions.*;

public class SolutionTests {
	private List<int[]> arrays = new ArrayList<>();
	private List<Integer> sums = new ArrayList<>();
	private List<List<IProblemSolver.Pair>> expectedResults = new ArrayList<>();

	@Before
	public void setTestCases() {
		case1();
		case2();
	}

	private void case1() {
		List<IProblemSolver.Pair> expected = new ArrayList<>();
		int[] array = { 1, 9, 1, 5, 7, 3, 9, -1 };
		int sum = 6;
		expected.add(new IProblemSolver.Pair(1, 5));
		expected.add(new IProblemSolver.Pair(1, 5));
		expected.add(new IProblemSolver.Pair(7, -1));

		arrays.add(array);
		sums.add(sum);
		expectedResults.add(expected);
	}

	private void case2() {
		List<IProblemSolver.Pair> expected = new ArrayList<>();
		int[] array = { -100, 75, -1, 84, 3, 3, 9, 1, 5, 7, 3, 9, -1, -1, 1 };
		int sum = 2;
		expected.add(new IProblemSolver.Pair(-1, 3));
		expected.add(new IProblemSolver.Pair(-1, 3));
		expected.add(new IProblemSolver.Pair(-1, 3));
		expected.add(new IProblemSolver.Pair(3, -1));
		expected.add(new IProblemSolver.Pair(3, -1));
		expected.add(new IProblemSolver.Pair(3, -1));
		expected.add(new IProblemSolver.Pair(3, -1));
		expected.add(new IProblemSolver.Pair(3, -1));
		expected.add(new IProblemSolver.Pair(3, -1));
		expected.add(new IProblemSolver.Pair(1, 1));

		arrays.add(array);
		sums.add(sum);
		expectedResults.add(expected);
	}

	private void makeTest(IProblemSolver solver) {
		for (int i = 0; i < arrays.size(); i++) {
			List<Pair> solution = solver.isSumIn(arrays.get(i), sums.get(i));
			List<Pair> expected = expectedResults.get(i);
			String errorMessage = "expected: " + expected.toString() + "   but was: " + solution.toString();
			for (Pair pair : expected) {
				assertTrue(errorMessage, solution.remove(pair));
			}
			assertTrue(errorMessage, solution.isEmpty());
		}
	}

	@Test
	public void testSolutionNaive() {
		makeTest(new SolutionNaive());
	}

	@Test
	public void testSolutionNaive2() {
		makeTest(new SolutionNaive2());
	}

}
