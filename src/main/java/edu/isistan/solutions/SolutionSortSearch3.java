package edu.isistan.solutions;

import edu.isistan.IProblemSolver;

import java.util.*;

public class SolutionSortSearch3 implements IProblemSolver {
    @Override
    public List<Pair> isSumIn(int[] data, int sum) {
        List<Pair> pairs = new ArrayList<>();
        
        HashMap<Integer, Integer> freqs = new HashMap<>();

        for (int dato : data) {
        	//no existe en el mapa
        	if(freqs.get(dato) == null) {
        		freqs.put(dato, 1);
        	}else {
        		freqs.put(dato, freqs.get(dato)+1);
        	}
        }
        
        final Integer[] nuevo = new Integer[freqs.size()];
        freqs.keySet().toArray(nuevo);
        Arrays.sort(nuevo);
        for (int i = 0; i < nuevo.length; i++) {
            Integer elem = nuevo[i];
            int found = (freqs.get(sum-elem)== null)? -1 : freqs.get(sum-elem);
            
            if(found >= 0){
                for (int j = 0; j < freqs.get(elem); j++) {
                    for (int k = 0; k < found; k++) {
                        pairs.add(new Pair(elem, sum-elem));
                    }
                }
            }
        }
        return pairs;
    }
}
