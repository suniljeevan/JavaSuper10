package edu.sunny.tool.greatfactorial.executor;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FactorialCallable {

    private int startIndex;
    private int[] result;

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setResult(int[] result) {
        this.result = result;
    }

    public int[] getResult() {
        return result;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getSize() {
        return result.length - startIndex;
    }

    public static FactorialCallable accumulate(List<Future<FactorialCallable>> results) throws ExecutionException, InterruptedException {
        FactorialCallable accumulatedResult = results.get(0).get();
        for (int index = 1; index < results.size(); index++) {
            FactorialCallable result = results.get(index).get();
            accumulatedResult = multiply(accumulatedResult, result);
        }

        return accumulatedResult;
    }

    private static FactorialCallable multiply(FactorialCallable result1Future, FactorialCallable result2Future) {
        int[] result1 = result1Future.result, result2 = result2Future.result, result = new int[result1Future.getSize() + result2Future.getSize()];
        int resultIndex = result.length - 1;

        for (int result2Index = result2.length - 1; result2Index >= result2Future.startIndex; result2Index--) {
            int carry = 0;
            resultIndex = result.length - (result2.length - result2Index) + 1;
            for (int result1Index = result1.length - 1; result1Index >= result1Future.startIndex; result1Index--, carry /= 10) {
                carry += result[--resultIndex] + result1[result1Index] * result2[result2Index];
                result[resultIndex] = carry % 10;
            }
            for (; carry > 0; carry /= 10)
                result[--resultIndex] = carry % 10;
        }

        FactorialCallable callableResult = new FactorialCallable();
        callableResult.setResult(result);
        callableResult.setStartIndex(resultIndex);

        return callableResult;
    }

}
