package edu.isistan.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import edu.isistan.IProblemSolver;

public class SolutionSortSearchWithExecutorService implements IProblemSolver{
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
			if( data[i]> sum - data[i]) 
				break;
			
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
        for (int i = start; i < end; i++) { // n veces
        	final int dat = data[i];
        	final int cond = sum - dat;

            final int found = Arrays.binarySearch(data, i + 1, data.length, cond); // log(n)
            if(found > -1) {
                int rightNeighbour = found+1, leftNeighbour = found-1;
                while(rightNeighbour < data.length && data[rightNeighbour] == data[found]){
                    pairs.add(new Pair(dat, data[rightNeighbour]));
                    rightNeighbour++;
                }
                while ( leftNeighbour > i  && data[leftNeighbour] == data[found]){
                    pairs.add(new Pair(dat, data[leftNeighbour]));
                    leftNeighbour--;
                }
                
                pairs.add(new Pair(dat, data[found]));
            }
        }
		return pairs;
	}
}
