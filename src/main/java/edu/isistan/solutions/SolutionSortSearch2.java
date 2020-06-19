package edu.isistan.solutions;

import edu.isistan.IProblemSolver;

import java.util.*;
import java.util.stream.IntStream;

public class SolutionSortSearch2 implements IProblemSolver {
    @Override
    public List<Pair> isSumIn(int[] data, int sum) {
        List<Pair> pairs = new ArrayList<>();
        int[] datos = data.clone(); //O(n)
        Arrays.sort(datos); // O(n*log(n))
        HashMap<Integer, Integer> freqs = new HashMap<>();
        int count = 0;
        int actual = datos[0];
        for (int dato : datos) {
            if (dato != actual) {
                freqs.put(actual, count);
                actual = dato;
                count = 0;
            }
            ++count;
        }
        final Integer[] nuevo = new Integer[freqs.size()];
        freqs.keySet().toArray(nuevo);
        Arrays.sort(nuevo);
        for (int i = 0; i < nuevo.length; i++) {
            Integer elem = nuevo[i];
            int found = Arrays.binarySearch(nuevo, i + 1, nuevo.length, sum - elem);
            if(found >= 0){
                for (int j = 0; j < freqs.get(elem); j++) {
                    for (int k = 0; k < freqs.get(nuevo[found]); k++) {
                        pairs.add(new Pair(elem, nuevo[found]));
                    }
                }
            }
        }
        return pairs;
    }
}
