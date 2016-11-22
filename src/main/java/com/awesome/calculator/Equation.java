package com.awesome.calculator;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class Equation {

  private final Map<String, Operand<Float>> operands = ImmutableMap.of(
      "add", (a, b) -> a + b,
      "minus", (a, b) -> a - b,
      "multiply", (a, b) -> a * b,
      "divide", (a, b) -> a / b
  );

  private final List<Operation> operations = new ArrayList<>();

  private Equation(float number) {
    operations.add(new Operation(number, operands.get("add")));
  }

  public static final Equation of(float number) {
    return new Equation(number);
  }

  public Equation add(float number) {
    return operation(number, operands.get("add"));
  }

  public Equation minus(float number) {
    return operation(number, operands.get("minus"));
  }

  public Equation multiply(float number) {
    return operation(number, operands.get("multiply"));
  }

  public Equation divide(float number) {
    if (number == 0) {
      throw new RuntimeException("Attempt to divide by 0");
    }
    return operation(number, operands.get("divide"));
  }

  public Equation operation(float number, Operand<Float> operand) {
    checkNotNull(operand);
    operations.add(new Operation(number, operand));
    return this;
  }

  public float eq() {
    float result = 0;
    for (Operation operation : operations) {
      result = operation.eq(result);
    }
    return result;
  }

  private class Operation {
    private float number = 0;
    private Operand<Float> operand;

    public Operation(float number, Operand operand) {
      this.number = number;
      this.operand = operand;
    }

    public float eq(float a) {
      return operand.apply(a, number);
    }
  }
}
