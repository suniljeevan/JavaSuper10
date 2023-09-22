package edu.sunny.tool.greatfactorial.array;

import java.util.Arrays;

public class ArrayFactorialCalculator {

    private final int maxDigits;
    private final int result[];

    public ArrayFactorialCalculator(int maxDigits) {
        this.maxDigits = maxDigits;
        this.result = getInitializedArray(maxDigits);
    }

    public int[] getResult() {
        return result;
    }

    public int calculate(int minNum, int maxNum) {
        int resultIndex = maxDigits - 1;

        for(int currentNumber = minNum; currentNumber <= maxNum; currentNumber++) {
            long carry = 0;
            int resultCurrentIndex;
            for(resultCurrentIndex = maxDigits; resultCurrentIndex > resultIndex; carry /= 10) {
                carry += result[--resultCurrentIndex] * currentNumber;
                result[resultCurrentIndex] = (int)(carry % 10);
            }
            for(; carry > 0; carry /= 10)
                result[--resultCurrentIndex] = (int)(carry % 10);
            resultIndex = resultCurrentIndex;
        }

        return resultIndex;
    }

    private static int[] getInitializedArray(int maxDigits) {
        int result[] = new int[maxDigits];
//        Arrays.fill(result, -9);
        result[maxDigits - 1] = 1;

        return result;
    }
}