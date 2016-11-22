package com.awesome.calculator;

import com.google.common.base.Joiner;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Equation {
  private Multimap<Integer, Operator> operatorsByWeight = LinkedListMultimap.create();

  public Equation() {
    registerOperator(new Operator("+", (a, b) -> a + b), 50);
    registerOperator(new Operator("-", (a, b) -> a - b), 50);
    registerOperator(new Operator("*", (a, b) -> a * b), 100);
    registerOperator(new Operator("/", (a, b) -> a / b), 100);
  }

  public Equation registerOperator(Operator operator, int weight) {
    operatorsByWeight.get(weight).add(operator); // TODO check if the operator does not already exists
    return this;
  }

  public double calculate(final String equationString) {
    String currentEquationString = equationString;

    List<Integer> weights = new ArrayList<>(operatorsByWeight.keySet());
    Collections.sort(weights);
    Collections.reverse(weights);
    for (int weight : weights) {
      currentEquationString = searchAndCompute(currentEquationString, operatorsByWeight.get(weight));
    }

    return Double.parseDouble(currentEquationString);
  }

  private String searchAndCompute(final String currentEquationString, final Collection<Operator> operators) {
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
