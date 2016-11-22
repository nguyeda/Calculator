package com.awesome.calculator;

import com.google.common.base.Joiner;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Equation {
  private LinkedList<Operator> operators = new LinkedList<>();

  public Equation() {
    registerOperator(new Operator("+", 50, (a, b) -> a + b));
    registerOperator(new Operator("-", 50, (a, b) -> a - b));
    registerOperator(new Operator("*", 100, (a, b) -> a * b));
  }

  public Equation registerOperator(Operator operator) {
    operators.add(operator);
    return this;
  }

  public double calculate(final String equationString) {
    String currentEquationString = equationString;
    currentEquationString = searchAndCompute(currentEquationString, operators);

    return Double.parseDouble(currentEquationString);
  }

  private String searchAndCompute(final String currentEquationString, final List<Operator> operators) {
    String regex = Joiner.on("|").join(operators.stream().map(Operator::getPattern).collect(Collectors.toList()));
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(currentEquationString);

    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      Double a = null;
      Double b = null;
      String sign = null;
      String match = matcher.group();
      for (int i = 1; i <= matcher.groupCount(); i++) {
        // the position of the group of interest may vary depending on the position of the operator pattern
        // TODO: this is a bit weak
        if (match.equals(matcher.group(i))) {
          a = Double.parseDouble(matcher.group(i + 1));
          sign = matcher.group(i + 2);
          b = Double.parseDouble(matcher.group(i + 3));
        }
      }

      String s = sign; // TODO: That sucks
      OperatorFunction fn = operators
          .stream()
          .filter(op -> op.getSign().equals(s))
          .findFirst()
          .orElseThrow(RuntimeException::new)
          .getFunction();

      matcher.appendReplacement(sb, String.valueOf(fn.apply(a, b)));
    }

    matcher.appendTail(sb);
    String newEquationString = sb.toString();

    return pattern.matcher(newEquationString).matches() ?
        searchAndCompute(newEquationString, operators) : newEquationString;
  }

}
