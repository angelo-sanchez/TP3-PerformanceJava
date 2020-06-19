package edu.isistan.solutions;

import edu.isistan.IProblemSolver;

import java.util.*;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class SolutionSortSearch implements IProblemSolver {
    @Override
    public List<Pair> isSumIn(int[] data, int sum) {
        List<Pair> pairs = new ArrayList<>();
        int[] datos = data.clone(); //O(n)
        Arrays.sort(datos);

        for (int i = 0; i < datos.length; i++) { // n veces
            int found = Arrays.binarySearch(datos, i + 1, datos.length, sum - datos[i]); // log(n)
            if(found >= 0) {
                int rightNeighbour = found+1, leftNeighbour = found-1;
                while(rightNeighbour < datos.length && datos[rightNeighbour] == datos[found]){
                    pairs.add(new Pair(datos[i], datos[rightNeighbour]));
                    rightNeighbour++;
                }
                while (leftNeighbour > 0 && datos[leftNeighbour] == datos[found]){
                    pairs.add(new Pair(datos[i], datos[leftNeighbour]));
                    leftNeighbour--;
                }
                pairs.add(new Pair(datos[i], datos[found]));
            }
        }
        return pairs;
    }
}
