package com.awesome.calculator;

@FunctionalInterface
public interface Operand<R> {

  R apply(R a, R b);
}
