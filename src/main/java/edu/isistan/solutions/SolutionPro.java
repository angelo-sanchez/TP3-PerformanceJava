package edu.isistan.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.isistan.IProblemSolver;

public class SolutionPro implements IProblemSolver {

	@Override
	public List<Pair> isSumIn(int[] data, int sum) {
		List<Pair> pairs = new ArrayList<>();
		Arrays.parallelSort(data);
		
		for (int i = 0; i < data.length; i++) {
			int j = Arrays.binarySearch(data, sum - data[i]);
				if (j > i)
					pairs.add(new Pair(data[i], data[j]));
		}

		return pairs;
	}

}
