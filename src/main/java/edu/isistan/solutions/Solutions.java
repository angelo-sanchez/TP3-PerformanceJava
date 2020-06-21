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
    	problemGen.genRandomProblem(10000);
    	int[] array =  { -100, 75, -1, 84, 3, 3, 9, 1, 5, 7, 3, 9, -1, -1, 1 };
    	sum = 6;
    	solution = new SolutionSortSearch3();
    	solution.isSumIn(array, 2);*/
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
    	problemGen.genRandomProblem(1000000);
    	data = problemGen.getData();
    	
    	//solution = new SolutionSortSearch(); 	//(min, avg, max) = (159,605, 162,571, 169,892)
    	solution = new SolutionNaive2(); 		//no termina
    	//solution = new SolutionNaive(); 		// no termina
    	//solution = new SolutionSortSearch3(); //(min, avg, max) = (688,241, 740,035, 842,555)
    	//solution = new SolutionPro(); 		//(min, avg, max) = (68,872, 72,460, 75,746)
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
