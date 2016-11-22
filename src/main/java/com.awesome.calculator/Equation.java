package com.awesome.calculator;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Equation {
  private LinkedList<Operator> operators = new LinkedList<>();

  public Equation() {
    registerOperator(new Operator("+", (a, b) -> a + b));
  }

  public Equation registerOperator(Operator operator) {
    operators.add(operator);
    return this;
  }

  public double calculate(final String equationString) {
    String currentEquationString = equationString;
    for (Operator operator : operators) {
      currentEquationString = searchAndCompute(currentEquationString, operator);
    }

    return Double.parseDouble(currentEquationString);
  }

  private String searchAndCompute(final String currentEquationString, final Operator operator) {
    Pattern pattern = operator.getPattern();
    Matcher matcher = pattern.matcher(currentEquationString);
    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      double a = Double.parseDouble(matcher.group(2));
      double b = Double.parseDouble(matcher.group(4));

      matcher.appendReplacement(sb, String.valueOf(operator.getFunction().apply(a, b)));
    }
    matcher.appendTail(sb);
    String newEquationString = sb.toString();
    return pattern.matcher(newEquationString).matches() ?
        searchAndCompute(newEquationString, operator) : newEquationString;
  }

}
