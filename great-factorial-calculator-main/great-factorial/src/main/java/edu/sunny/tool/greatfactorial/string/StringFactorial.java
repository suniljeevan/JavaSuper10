package edu.sunny.tool.greatfactorial.string;

import java.util.*;
public class StringFactorial {
    /* This algorithm
     * Works much Slower
     */
    public static void main(String args[]) {
        String result = "1", tempResult;
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int input = sc.nextInt(), resultIndex;

        long start = System.currentTimeMillis();

        for(int currentNumber = 2; currentNumber <= input; currentNumber++) {
            long carry = 0;
            for(resultIndex = result.length() - 1, tempResult = ""; resultIndex >= 0; resultIndex--, carry /= 10) {
                carry += (result.charAt(resultIndex) - 48) * currentNumber;
                tempResult = (int)(carry % 10) + tempResult;
            }
            result = carry != 0 ? carry + tempResult : tempResult;
        }

        long diff = System.currentTimeMillis() - start;
        System.out.println(String.format("Calculation Time: %d Minute(s) %f Second(s)", diff / 60000, diff % 60000 / 1000.0));

        if(input > 10)
            System.out.println(String.format("%c.%se+%d (TRUNCATED VALUE)", result.charAt(0), "" + result.charAt(1)
                    + result.charAt(2) + result.charAt(3) + result.charAt(4), (result.length() - 1)));

        System.out.println(result.length() + " Digit(s)");
        System.out.print(result);
    }
}