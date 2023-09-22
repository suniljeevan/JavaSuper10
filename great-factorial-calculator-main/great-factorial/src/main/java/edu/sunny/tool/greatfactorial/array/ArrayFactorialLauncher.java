package edu.sunny.tool.greatfactorial.array;

import java.util.Scanner;

public class ArrayFactorialLauncher {

    /* This algorithm
     * Works much Faster
     */
    public static void main(String args[]) {
        int maxDigits = 1_00_00_000;
        ArrayFactorialCalculator calc = new ArrayFactorialCalculator(maxDigits);

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int input = sc.nextInt();

        long start = System.currentTimeMillis();

        int resultIndex = calc.calculate(2, input);

        long diff = System.currentTimeMillis() - start;

        int result[] = calc.getResult();

        for(int tempIndex = resultIndex; tempIndex < maxDigits; tempIndex++)
            System.out.print(result[tempIndex]);
        
        System.out.println(String.format("\n%d Digit(s)", maxDigits - resultIndex));

        if(input > 10)
            System.out.println(String.format("%d.%se+%d (TRUNCATED VALUE)", result[resultIndex], "" + result[resultIndex + 1]
                    + result[resultIndex + 2] + result[resultIndex + 3] + result[resultIndex + 4], (maxDigits - 1 - resultIndex)));

        System.out.println(String.format("Calculation Time: %d Minute(s) %f Second(s)", diff / 60000, diff % 60000 / 1000.0));
    }

}
