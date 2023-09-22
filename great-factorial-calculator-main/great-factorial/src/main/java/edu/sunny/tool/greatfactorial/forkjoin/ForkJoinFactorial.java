package edu.sunny.tool.greatfactorial.forkjoin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinFactorial {

    public static void main( String[] args ) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        long input = sc.nextLong();

        long start = System.currentTimeMillis();

        List<Byte> revFactorial = forkJoinPool.invoke(new FactorialTask(input, 1L, input));

        long diff = System.currentTimeMillis() - start;
        System.out.println(String.format("\nCalculation Time: %d Minute(s) %f Second(s)", diff / 60000, diff % 60000 / 1000.0));

        Collections.reverse(revFactorial);
        revFactorial.stream().forEach(System.out::print);
    }
}

class FactorialTask extends RecursiveTask<List<Byte>> {
    private long input, left, right;

    public FactorialTask(Long input, Long left, Long right) {
        this.input = input;
        this.left = left;
        this.right = right;
    }

    @Override
    protected List<Byte> compute() {
        long range = right - left + 1;
        if (eligibleForSubTask(range)) {
            long mid = left + (range / 2);
            FactorialTask firstHalfTask = new FactorialTask(input, left, mid);
            FactorialTask secondHalfTask = new FactorialTask(input, mid + 1, right);
            secondHalfTask.fork().invoke();
            return multiply(firstHalfTask.compute(),secondHalfTask.join());
        } else {
            return selfCompute(left, right);
        }
    }

    private boolean eligibleForSubTask(long range) {
//        if(input > 10_000)
//            return left > (input/2) ? range > 500 : range > 1_000;
//        else
//            return left > (input/2) ? range > 50 : range > 100;
        return range > 10;
    }

    private List<Byte> selfCompute(long left, long right) {
        List<Byte> initial = addDigits(new ArrayList<>(), left);
        List<Byte> product = new ArrayList<>();

        while(++left <= right) {
            product.clear();
            multiply(initial, left, product);
            initial.clear();
            initial.addAll(product);
        }
        return product;
    }

    private List<Byte> multiply(List<Byte> left, List<Byte> right) {
        List<Byte> product = new ArrayList<>();
        List<Byte> bigger = left.size() >= right.size()? left : right;
        List<Byte> smaller = left.size() < right.size()? left : right;

        for(int smallIndex = 0; smallIndex < smaller.size() ; smallIndex++) {
            long carry = 0;
            for(int biggerIndex = 0; biggerIndex < bigger.size() ; biggerIndex++) {
                int productIndex = smallIndex + biggerIndex;
                if(productIndex > product.size() - 1) {
                    long tempProduct = carry + smaller.get(smallIndex) * bigger.get(biggerIndex);
                    carry = tempProduct / 10;
                    product.add((byte) (tempProduct % 10));
                } else {
                    long tempProduct = carry + product.remove(productIndex) + smaller.get(smallIndex) * bigger.get(biggerIndex);
                    carry = tempProduct / 10;
                    product.add(productIndex, (byte) (tempProduct % 10));
                }
            }
            addDigits(product, carry);
        }
        return product;
    }

    private void multiply(List<Byte> left, Long right, List<Byte> product) {
        long carry = 0;
        for(int index = 0; index < left.size() ; index++) {
            if(index > product.size() - 1) {
                long tempProduct = carry + right * left.get(index);
                carry = tempProduct / 10;
                product.add((byte) (tempProduct % 10));
            } else {
                long tempProduct = carry + product.remove(index) + right * left.get(index);
                carry = tempProduct / 10;
                product.add(index, (byte) (tempProduct % 10));
            }
        }
        addDigits(product, carry);
    }

    private List<Byte> addDigits(List<Byte> product, long number) {
        for(; number > 0; number /= 10)
            product.add((byte) (number%10));
        return product;
    }
}
