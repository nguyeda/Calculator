package com.awesome.calculator;

import java.util.regex.Pattern;

public class Operator {

  private final String sign;
  private final Pattern pattern;
  private final OperatorFunction function;

  public Operator(String sign, OperatorFunction function) {
    this.sign = "+";
    this.function = function;
    this.pattern = Pattern.compile("((\\d+\\.?\\d*)\\s*(" + Pattern.quote(sign) + ")\\s*(\\d+\\.?\\d*))+");
  }

  public String getSign() {
    return sign;
  }

  public Pattern getPattern() {
    return pattern;
  }

  public OperatorFunction getFunction() {
    return function;
  }
}
