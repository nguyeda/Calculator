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
   * Registers a new custom operation using a binary operator.
   *
   * @param number the next number to use in the operation
   * @param operator the operation to execute
   * @return the current {@link Equation} instance
   */
  public Equation operation(double number, BinaryOperator<Double> operator) {
    operations.add(new Operation(number, operator));
    return this;
  }

  /**
   * Registers a new custom operation using a unary operator.
   *
   * @param operator the operation to execute
   * @return the current {@link Equation} instance
   */
  public Equation operation(UnaryOperator<Double> operator) {
    operations.add(new Operation(operator));
    return this;
  }

  /**
   * Computes the equation result.
   *
   * This will process all operations in their registration order. The result of each operation will be used as the
   * first parameter in the method {@link BinaryOperator#apply(Object, Object)}.
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
    private final Double number;
    private final BinaryOperator<Double> binaryOperator;
    private final UnaryOperator<Double> unaryOperator;

    Operation(double number, BinaryOperator<Double> operator) {
      checkNotNull(operator);
      this.number = number;
      this.unaryOperator = null;
      this.binaryOperator = operator;
    }

    Operation(UnaryOperator<Double> operator) {
      checkNotNull(operator);
      this.number = null;
      this.unaryOperator = operator;
      this.binaryOperator = null;
    }

    double eq(double a) {
      if (unaryOperator != null) {
        return unaryOperator.apply(a);
      }

      return binaryOperator.apply(a, number);
    }
  }
}
