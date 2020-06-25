package edu.isistan.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.isistan.IProblemSolver;

public class SolutionProWithExecutorService implements IProblemSolver {
	private static final int TASKS_FOR_CORE = 16;
	private int[] data;
	private int sum;

	@Override
	public List<Pair> isSumIn(int[] data, int sum) {
		List<Pair> pairs = new ArrayList<>();
		List<Future<List<Pair>>> futures = new ArrayList<>();

		Arrays.parallelSort(data);
		final int cores = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService = Executors.newFixedThreadPool(cores);
		final int calculatedStep = data.length / (cores * TASKS_FOR_CORE);
		final int step = (calculatedStep == 0) ? 1 : calculatedStep;
		this.data = data;
		this.sum = sum;

		// Assign parts of the data array into a concurrent task
		for (int i = 0; i < data.length; i += step) {
			final int start = i;
			final int limit = i + step;
			final int end = (limit > data.length) ? data.length : limit;
			futures.add(executorService.submit(() -> {
				return findPairsInSubArray(start, end);
			}));
		}

		executorService.shutdown();

		for (Future<List<Pair>> future : futures) {
			try {
				pairs.addAll(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		return pairs;
	}

	private List<Pair> findPairsInSubArray(int start, int end) {
		List<Pair> pairs = new ArrayList<>();

		for (int i = start; i < end; i++) {
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
