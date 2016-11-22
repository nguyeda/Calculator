package com.awesome.calculator;

import java.util.regex.Pattern;

public class Operator {

  private final String sign;
  private final String pattern;
  private final int weight;
  private final OperatorFunction function;

  public Operator(String sign, int weight, OperatorFunction function) {
    this.sign = sign;
    this.weight = weight;
    this.function = function;
    this.pattern = "((-?\\d+\\.?\\d*)\\s*(" + Pattern.quote(sign) + ")\\s*(-?\\d+\\.?\\d*))+";
  }

  public String getSign() {
    return sign;
  }

  public String getPattern() {
    return pattern;
  }

  public int getWeight() {
    return weight;
  }

  public OperatorFunction getFunction() {
    return function;
  }
}
