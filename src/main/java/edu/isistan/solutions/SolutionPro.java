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
			if (i > 0 && data[i] == data[i-1])
				continue;
			int j = Arrays.binarySearch(data, sum - data[i]);
				if (j > i) {
					int cantI = 1;
					int cantJ = 1;
					for (int aux = i + 1; aux < data.length && data[aux] == data[i]; aux++) {
						cantI++;
					}
					for (int aux = j + 1; aux < data.length && data[aux] == data[j]; aux++) {
						cantJ++;
					}
					for (int aux = j - 1; aux >= 0 && data[aux] == data[j]; aux--) {
						cantJ++;
					}
					for (int aux = 0; aux < cantI * cantJ; aux++)
						pairs.add(new Pair(data[i], data[j]));
				}
		}

		return pairs;
	}

}
