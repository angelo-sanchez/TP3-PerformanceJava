package edu.isistan.solutions;

import edu.isistan.IProblemSolver;

import java.util.*;

public class SolutionSortSearchWithThread implements IProblemSolver {
	private int[] datos;
	private int sum;
	List<Pair> pairs = new Vector<>();
	
    @Override
    public List<Pair> isSumIn(int[] data, int sum) {
        datos = data;
        this.sum = sum;
        Arrays.parallelSort(datos);

        int inic = 0;
        boolean cont = true;
        int salto = 10000;
        int fin = 0;
        
        ArrayList<Thread> threads = new ArrayList<>();
        while(cont) {
        	fin = inic+salto;
        	if(fin > datos.length) {
        		fin = datos.length;
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
        for (int i = inic; i < fin; i++) { // n veces
        	if(datos[i]> sum - datos[i])
        		break;
            int found = Arrays.binarySearch(datos, i + 1, datos.length, sum - datos[i]); // log(n)
            if(found >= 0) {
                int rightNeighbour = found+1, leftNeighbour = found-1;
                while(rightNeighbour < datos.length && datos[rightNeighbour] == datos[found]){
                    pairs.add(new Pair(datos[i], datos[rightNeighbour]));
                    rightNeighbour++;
                }
                while (leftNeighbour > 0 && leftNeighbour > i  && datos[leftNeighbour] == datos[found]){
                    pairs.add(new Pair(datos[i], datos[leftNeighbour]));
                    leftNeighbour--;
                }
                pairs.add(new Pair(datos[i], datos[found]));

            }
        }
    }
}
