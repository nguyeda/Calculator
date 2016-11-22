package com.awesome.calculator;

import com.google.common.base.Joiner;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public class Equation {
  private Multimap<Integer, Operator> operatorsByWeight = LinkedListMultimap.create();

  public Equation() {
    registerOperator(new Operator("+", (a, b) -> a + b), 50);
    registerOperator(new Operator("-", (a, b) -> a - b), 50);
    registerOperator(new Operator("*", (a, b) -> a * b), 100);
    registerOperator(new Operator("/", (a, b) -> a / b), 100);
  }

  /**
   * Adds a new operator.
   *
   * @param operator
   * @param weight   defines the operator priority, heavier operators get computed first.
   * @return the current {@link Equation} instance
   */
  public Equation registerOperator(Operator operator, int weight) {
    operatorsByWeight.get(weight).add(operator); // TODO check if the operator does not already exists
    return this;
  }

  /**
   * Removes all existing operators.
   *
   * @return the current {@link Equation} instance
   */
  public Equation clearOperators() {
    operatorsByWeight.clear();
    return this;
  }

  /**
   * Calculates the result of an equation, using the registered operators.
   *
   * @param equationString a string representing the equation to calculate
   * @return the equation result
   */
  public double calculate(final String equationString) {
    String currentEquationString = equationString;

    List<Integer> weights = new ArrayList<>(operatorsByWeight.keySet());
    Collections.sort(weights);
    Collections.reverse(weights);
    for (int weight : weights) {
      currentEquationString = searchAndCompute(currentEquationString, operatorsByWeight.get(weight));
    }

    try {
      return Double.parseDouble(currentEquationString);
    } catch (NumberFormatException e) {
      // If the result is not a number, it means that some part of the equation could not be solved
      // probably because of some unknown operators
      throw new EquationParserException("Malformed equation " + currentEquationString, equationString);
    }
  }

  private String searchAndCompute(final String currentEquationString, final Collection<Operator> operators) {
    // Build a map of operators by sign, this is done for each computation in case some new operators are created or
    // removed between two calls
    Map<String, Operator> operatorBySign = operators.stream().collect(Collectors.toMap(Operator::getSign, identity()));

    // Combine all operator patterns into one big, this will allow us to process operation with the same
    // weight from left to right
    String regex = Joiner.on("|").join(operators.stream().map(Operator::getPattern).collect(Collectors.toList()));
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(currentEquationString);

    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      // the first step is to identify the components of our operation: a <sign> b
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

      // find the operator for this operation
      OperatorFunction fn = operatorBySign.get(sign).getFunction();
      // replace this bit with the result
      matcher.appendReplacement(sb, String.valueOf(fn.apply(a, b)));
    }

    matcher.appendTail(sb);
    String newEquationString = sb.toString();

    // run again if there is some other matches for this operator, otherwise, return the new equation string
    return pattern.matcher(newEquationString).matches() ?
        searchAndCompute(newEquationString, operators) : newEquationString;
  }

}
