package edu.isistan.solutions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import edu.isistan.IProblemSolver;
import edu.isistan.ProblemGen;

@State(Scope.Benchmark)
public class Solutions {
	private static int[] data;
	private static IProblemSolver solution;
	private static int sum = 0;
	private static int tamArr = 50000000;

    public static void main(String[] args) throws Exception {

    	//Descomentar linea para ejecutar los test con Benchmark
    	//org.openjdk.jmh.Main.main(args);

    	//Metodo usado para el testeo de memoria que consume cada solución con visualVM
    	testMemory();
    }
    
    public static void testMemory() {
    	ProblemGen problemGen = new ProblemGen();
    	problemGen.genRandomProblem(tamArr);
    	sum = (int)(Math.random() * 2 * Integer.MAX_VALUE + Integer.MIN_VALUE/2);
		
    	//solution = new SolutionSortSearch();
    	//solution = new SolutionSortSearchWithThread();
    	//solution = new SolutionSortSearchWithExecutorService();
    	//solution = new SolutionNaive2();
    	//solution = new SolutionNaive();
    	//solution = new SolutionSortSearch3();
    	//solution = new SolutionPro();
    	solution = new SolutionProThread();
    	//solution = new SolutionProWithExecutorService();

		System.out.println(solution.isSumIn(problemGen.getData(), sum).size());
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Setup(Level.Invocation)
    public static void setup() {
    	ProblemGen problemGen = new ProblemGen();
    	problemGen.genRandomProblem(tamArr);
    	data = problemGen.getData();
    	
    	//solution = new SolutionSortSearch();
    	//solution = new SolutionSortSearchWithThread();
    	//solution = new SolutionSortSearchWithExecutorService();
    	//solution = new SolutionNaive2();
    	//solution = new SolutionNaive();
    	//solution = new SolutionSortSearch3();
    	//solution = new SolutionPro();
    	solution = new SolutionProThread();
    	//solution = new SolutionProWithExecutorService();
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
