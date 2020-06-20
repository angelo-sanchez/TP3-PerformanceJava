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
			if (i > 0 && data[i] == data[i - 1])
				continue;

			// If the current value needs a lower value to make a pair, then the loop must
			// finish because it's an ordered array
			if (data[i] > sum - data[i])
				break;

			if (sum == data[i] * 2) {
				int cantI = frequencyOfI(data, i);
				if (cantI > 1) {
					// If the pair is with itself and it's a repeated value
					int combinations = sum(cantI);
					for (int aux = 0; aux < combinations; aux++)
						pairs.add(new Pair(data[i], data[i]));
				}
			} else {
				int j = Arrays.binarySearch(data, i + 1, data.length, sum - data[i]);
				if (j > 0) {
					int cantI = frequencyOfI(data, i);
					int cantJ = frequencyForJ(data, j);
					// Add as many pairs as combinations of cantI with cantJ
					for (int aux = 0; aux < cantI * cantJ; aux++)
						pairs.add(new Pair(data[i], data[j]));
				}
			}
		}

		return pairs;
	}

	private int sum(int n) {
		return (n * (n - 1)) / 2;
	}

	private int frequencyForJ(int[] data, int j) {
		int cantJ = 1;
		for (int aux = j + 1; aux < data.length && data[aux] == data[j]; aux++) {
			cantJ++;
		}
		for (int aux = j - 1; aux >= 0 && data[aux] == data[j]; aux--) {
			cantJ++;
		}
		return cantJ;
	}

	private int frequencyOfI(int[] data, int i) {
		int cantI = 1;
		for (int aux = i + 1; aux < data.length && data[aux] == data[i]; aux++) {
			cantI++;
		}
		return cantI;
	}

}
