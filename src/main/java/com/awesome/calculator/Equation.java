package com.awesome.calculator;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Equation {
  private final float initialNumber;
  private final List<Operation> operations = new ArrayList<>();

  public Equation(float initialNumber) {
    this.initialNumber = initialNumber;
  }

  public Equation add(float number) {
    return operation(number, (a, b) -> a + b);
  }

  public Equation minus(float number) {
    return operation(number, (a, b) -> a - b);
  }

  public Equation multiply(float number) {
    return operation(number, (a, b) -> a * b);
  }

  public Equation divide(float number) {
    if (number == 0) {
      throw new RuntimeException("Attempt to divide by 0");
    }
    return operation(number, (a, b) -> a / b);
  }

  public Equation operation(float number, Operand<Float> operand) {
    checkNotNull(operand);
    operations.add(new Operation(number, operand));
    return this;
  }

  public float eq() {
    float result = initialNumber;
    for (Operation operation : operations) {
      result = operation.eq(result);
    }
    return result;
  }

  public float getInitialNumber() {
    return initialNumber;
  }

  public List<Operation> getOperations() {
    return operations;
  }

  class Operation {
    private float number = 0;
    private Operand<Float> operand;

    public Operation(float number, Operand<Float> operand) {
      this.number = number;
      this.operand = operand;
    }

    public float eq(float a) {
      return operand.apply(a, number);
    }
  }
}
