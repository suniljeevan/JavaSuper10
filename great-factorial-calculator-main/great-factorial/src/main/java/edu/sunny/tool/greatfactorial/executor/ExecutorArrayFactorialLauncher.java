package edu.sunny.tool.greatfactorial.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorArrayFactorialLauncher {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int input = sc.nextInt();

        List<ExecutorArrayFactorialTask> tasks = new ArrayList<>();

        int chunkSize = 1_00_000;

        for (int startNum = 1; startNum <= input; startNum += chunkSize)
            tasks.add(new ExecutorArrayFactorialTask(startNum, Math.min(input, startNum + chunkSize - 1)));

        long start = System.currentTimeMillis();

        FactorialCallable accumulatedResult = FactorialCallable.accumulate(executorService.invokeAll(tasks));

        long diff = System.currentTimeMillis() - start;
        System.out.println(String.format("Calculation Time: %d Minute(s) %f Second(s)", diff / 60000, diff % 60000 / 1000.0));

        int[] result = accumulatedResult.getResult();
        int resultIndex = accumulatedResult.getStartIndex();

        int maxDigits = result.length;
        if(input > 10)
            System.out.println(String.format("%d.%se+%d (TRUNCATED VALUE)", result[resultIndex], "" + result[resultIndex + 1]
                    + result[resultIndex + 2] + result[resultIndex + 3] + result[resultIndex + 4], accumulatedResult.getSize() - 1));

        System.out.println(accumulatedResult.getSize() + " Digit(s)");

        for(; resultIndex < maxDigits; resultIndex++)
            System.out.print(result[resultIndex]);

        System.exit(0);
    }
}
