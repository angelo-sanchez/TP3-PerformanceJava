package edu.isistan.solutions;

import java.util.Arrays;
import java.util.stream.IntStream;

import edu.isistan.IProblemSolver;
import edu.isistan.ProblemGen;

public class Solutions {

    public static void main(String[] args) {

        ProblemGen problemGen = new ProblemGen();

        IProblemSolver naive = new SolutionSortSearch();
        IProblemSolver sort = new SolutionSortSearch2();
//        int suma = (int) (Math.random() * 2 * Integer.MAX_VALUE + Integer.MIN_VALUE / 2);
        int suma = 0;
        System.out.println("Número a encontrar: " + suma);
        for (int i = 0; i < 20; i++) {
            problemGen.genRandomProblem(100000);
            System.out.println("Se buscará en un arreglo con números entre: " + problemGen.minMax());
//			System.out.println(Arrays.toString(problemGen.getData()));
            long start = System.currentTimeMillis(); //acá no está haciendo el warm up para empezar con el benchmarking!!
            System.out.print("SORTED AND NEIGHBOUR: -- Pairs: " + naive.isSumIn(problemGen.getData(), suma).size());
            start = System.currentTimeMillis() - start;
            System.out.println(" // " + start + " Milisegundos.\n");
            start = System.currentTimeMillis();
            System.out.print("SORTED AND MAPPED -- Pairs: " + sort.isSumIn(problemGen.getData(), suma).size());
            start = System.currentTimeMillis() - start;
            System.out.println(" // " + start + " Milisegundos.\n\n");
        }
    }
}
