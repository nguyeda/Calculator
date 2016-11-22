package com.awesome.calculator;

@FunctionalInterface
public interface UnaryOperator<R> {

  R apply(R a);
}
