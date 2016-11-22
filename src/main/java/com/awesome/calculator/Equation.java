package com.awesome.calculator;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Equation {

  private final Map<String, Operand<Float>> operands = ImmutableMap.of(
      "add", (a, b) -> a + b
  );

  private final List<Operation> operations = new ArrayList<>();

  private Equation(float number) {
    operations.add(new Operation(number, operands.get("add")));
  }

  public static final Equation of(float number) {
    return new Equation(number);
  }

  public Equation add(float number) {
    operations.add(new Operation(number, operands.get("add")));
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
