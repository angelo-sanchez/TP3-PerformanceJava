package edu.isistan.solutions;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.*;

import edu.isistan.IProblemSolver;
import edu.isistan.ProblemGen;

@State(Scope.Benchmark)
public class Solutions {
	private static int[] data;
	private static IProblemSolver solution;
	private static int sum = 0;

    public static void main(String[] args) throws Exception {

    	org.openjdk.jmh.Main.main(args);
    	/*ProblemGen problemGen = new ProblemGen();
    	problemGen.genRandomProblem(1000000);
    	int[] array =  { -100, 75, -1, 84, 3, 3, 9, 1, 5, 7, 3, 9, -1, -1, 1 };
    	sum = 6;
    	solution = new SolutionSortSearch();
    	solution.isSumIn(problemGen.getData(), 0);*/
    }
    
    
    public static void test() {
        ProblemGen problemGen = new ProblemGen();

        IProblemSolver naive = new SolutionSortSearch();
        IProblemSolver sort = new SolutionSortSearch2();
//        int suma = (int) (Math.random() * 2 * Integer.MAX_VALUE + Integer.MIN_VALUE / 2);
        int suma = 0;
        System.out.println("Número a encontrar: " + suma);
        for (int i = 0; i < 20; i++) {
            problemGen.genRandomProblem(1000);
            System.out.println("Se buscará en un arreglo con números entre: " + problemGen.minMax());
//			System.out.println(Arrays.toString(problemGen.getData()));
            long start = System.currentTimeMillis(); //acá no está haciendo el warm up para empezar con el benchmarking!!
            System.out.print("SORTED AND NEIGHBOUR: -- Pairs: " + naive.isSumIn(problemGen.getData(), suma).size()+"\n");
            start = System.currentTimeMillis() - start;
            System.out.println(" // " + start + " Milisegundos.\n\n");
            start = System.currentTimeMillis();
            System.out.print("SORTED AND MAPPED -- Pairs: " + sort.isSumIn(problemGen.getData(), suma).size());
            start = System.currentTimeMillis() - start;
            System.out.println(" // " + start + " Milisegundos.\n\n");
        }
        int i = 0;
    }
    
    @Setup(Level.Invocation)
    public void setup() {
    	ProblemGen problemGen = new ProblemGen();
    	problemGen.genRandomProblem(2000000);
    	data = problemGen.getData();
    	
    	//solution = new SolutionSortSearch(); 	//(min, avg, max) = (113,338, 122,860, 127,389)
    	//solution = new SolutionNaive2(); 		//no termina
    	//solution = new SolutionNaive(); 		// no termina
    	//solution = new SolutionSortSearch3(); //no termina con tanto
    	//solution = new SolutionPro(); 		//(min, avg, max) = (142,607, 151,981, 162,089)
    	solution = new SolutionProThread();		//(min, avg, max) = (116,865, 125,099, 133,479)
    }
    
    @Benchmark
    @Fork(value = 5, warmups = 0)
    @Warmup(iterations = 5)
    @Measurement(iterations = 5)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public static void test2() {
        
        solution.isSumIn(data, sum);
    }
}
