package com.awesome.calculator;

public class EquationParserException extends RuntimeException {
  private String equation;

  public EquationParserException(String message, String equation) {
    super(message);
    this.equation = equation;
  }

  public String getEquation() {
    return equation;
  }
}
