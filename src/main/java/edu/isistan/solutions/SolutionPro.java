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
			final int currentValue = data[i];
			if (i > 0 && currentValue == data[i - 1])
				continue;

			// If the current value needs a lower value to make a pair, then the loop must
			// finish because it's an ordered array
			final int complementaryValue = sum - currentValue;
			if (currentValue > complementaryValue)
				break;

			if (sum == currentValue * 2) {
				int cantI = frequencyOfCurrentValue(data, i);
				if (cantI > 1) {
					// If the pair is with itself and it's a repeated value
					int combinations = sum(cantI);
					for (int aux = 0; aux < combinations; aux++)
						pairs.add(new Pair(currentValue, currentValue));
				}
			} else {
				int j = Arrays.binarySearch(data, i + 1, data.length, complementaryValue);
				if (j > 0) {
					int freqCurrentValue = frequencyOfCurrentValue(data, i);
					int freqComplementaryValue = frequencyOfComplementaryValue(data, j);
					// Add as many pairs as combinations of the frequencies
					for (int aux = 0; aux < freqCurrentValue * freqComplementaryValue; aux++)
						pairs.add(new Pair(currentValue, complementaryValue));
				}
			}
		}

		return pairs;
	}

	private int sum(int n) {
		return (n * (n - 1)) / 2;
	}

	private int frequencyOfComplementaryValue(int[] data, int index) {
		int cantJ = 1;
		final int currentValue = data[index];
		for (int aux = index + 1; aux < data.length && data[aux] == currentValue; aux++) {
			cantJ++;
		}
		for (int aux = index - 1; aux >= 0 && data[aux] == currentValue; aux--) {
			cantJ++;
		}
		return cantJ;
	}

	private int frequencyOfCurrentValue(int[] data, int index) {
		int cantI = 1;
		final int currentValue = data[index];
		for (int aux = index + 1; aux < data.length && data[aux] == currentValue; aux++) {
			cantI++;
		}
		return cantI;
	}

}
