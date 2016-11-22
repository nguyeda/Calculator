package com.awesome.calculator;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Equation utility class.
 * <p>
 * This class offers 4 methods for basic operations
 * <ul>
 * <li>{@link Equation#add(double)}</li>
 * <li>{@link Equation#minus(double)}</li>
 * <li>{@link Equation#multiply(double)} </li>
 * <li>{@link Equation#divide(double)} </li>
 * </ul>
 * <p>
 * Usage:
 * <pre>
 *     new Equation(12).add(5).minus(3).multiply(6).divide(8).eq()
 * </pre>
 * <p>
 * It also allows to add custom operations:
 * <p>
 * <pre>
 *     new Equation(17).operation(5, (a, b) -> a % b).eq()
 * </pre>
 */
public class Equation {
  private final double initialNumber;
  private final List<Operation> operations = new ArrayList<>();

  public Equation(double initialNumber) {
    this.initialNumber = initialNumber;
  }

  /**
   * Convenient method to add a number.
   *
   * @param number the number to add
   * @return the current {@link Equation} instance
   */
  public Equation add(double number) {
    return operation(number, (a, b) -> a + b);
  }

  /**
   * Convenient method to subtract a number.
   *
   * @param number the number to subtract
   * @return the current {@link Equation} instance
   */
  public Equation minus(double number) {
    return operation(number, (a, b) -> a - b);
  }

  /**
   * Convenient method to multiply by a number.
   *
   * @param number the number to multiply
   * @return the current {@link Equation} instance
   */
  public Equation multiply(double number) {
    return operation(number, (a, b) -> a * b);
  }

  /**
   * Convenient method to divide by a number.
   *
   * @param number the number to divide
   * @return the current {@link Equation} instance
   */
  public Equation divide(double number) {
    if (number == 0) {
      throw new RuntimeException("Attempt to divide by 0");
    }
    return operation(number, (a, b) -> a / b);
  }

  /**
   * Registers a new custom operation.
   *
   * @param number the next number to use in the operation
   * @param operand the operation to execute
   * @return the current {@link Equation} instance
   */
  public Equation operation(double number, Operand<Double> operand) {
    checkNotNull(operand);
    operations.add(new Operation(number, operand));
    return this;
  }

  /**
   * Computes the equation result.
   *
   * This will process all operations in their registration order. The result of each operation will be used as the
   * first parameter in the method {@link Operand#apply(Object, Object)}.
   *
   * Note that the {@see <a href="https://en.wikipedia.org/wiki/Order_of_operations">order of operations</a>}
   * is not supported at this stage
   *
   * @return the equation result
   */
  public double eq() {
    double result = initialNumber;
    for (Operation operation : operations) {
      result = operation.eq(result);
    }
    return result;
  }

  public double getInitialNumber() {
    return initialNumber;
  }

  public List<Operation> getOperations() {
    return operations;
  }

  class Operation {
    private double number = 0;
    private Operand<Double> operand;

    public Operation(double number, Operand<Double> operand) {
      this.number = number;
      this.operand = operand;
    }

    public double eq(double a) {
      return operand.apply(a, number);
    }
  }
}
