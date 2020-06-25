package edu.isistan.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import edu.isistan.IProblemSolver;

public class SolutionProThread implements IProblemSolver{
	List<Pair> pairs = new Vector<>();
	private int[] data;
	private int sum;
	
	@Override
	public List<Pair> isSumIn(int[] data, int sum) {
		
		Arrays.parallelSort(data);
		this.data = data;
		this.sum = sum;
		
        int inic = 0;
        boolean cont = true;
        int salto = 11000;
        int fin = 0;
        
        ArrayList<Thread> threads = new ArrayList<>();
        while(cont) {
        	fin = inic+salto;
        	if(fin > data.length) {
        		fin = data.length;
        		cont = false;
        	}
        	int i = inic;
        	int f= fin;

        	Thread thread = new Thread(){
        		public void run(){
        			search(i, f);
        		}
        	};
        	thread.start();
        	threads.add(thread);
        			   
        	inic = fin;
        }

        for (int x=0;x<threads.size();x++)
			try {
				threads.get(x).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		return pairs;
	}
	
	private void search(int inic, int fin) {
		for (int i = inic; i < fin; i++) {
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
