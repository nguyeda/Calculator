package com.awesome.calculator;

@FunctionalInterface
public interface BinaryOperator<R> {

  R apply(R a, R b);
}
