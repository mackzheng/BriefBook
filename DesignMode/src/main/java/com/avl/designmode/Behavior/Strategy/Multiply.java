package com.avl.designmode.Behavior.Strategy;

public class Multiply extends AbstractCalculator implements ICalculator {
    @Override
    public int calculate(String exp) {

        int arrayInt[] = split(exp,"\\*");
        return arrayInt[0]*arrayInt[1];
    }
}
