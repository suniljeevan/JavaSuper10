package edu.sunny.tool.greatfactorial.executor;

import edu.abhi.tool.greatfactorial.array.ArrayFactorialCalculator;

import java.util.concurrent.Callable;

public class ExecutorArrayFactorialTask implements Callable<FactorialCallable> {

    private final int startNum;
    private final int endNum;

    public ExecutorArrayFactorialTask(int startNum, int endNum) {
        this.startNum = startNum;
        this.endNum = endNum;
    }

    @Override
    public FactorialCallable call() throws Exception {
        ArrayFactorialCalculator calc = new ArrayFactorialCalculator(20_00_000);

        int resultIndex = calc.calculate(startNum, endNum);

        FactorialCallable callableResult = new FactorialCallable();
        callableResult.setStartIndex(resultIndex);
        callableResult.setResult(calc.getResult());

        return callableResult;
    }

}
