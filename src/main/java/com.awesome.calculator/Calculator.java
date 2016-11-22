package com.awesome.calculator;

import java.util.Scanner;

public class Calculator {
  public static void main(String... args) {
    Equation instance = new Equation()
        // register a synonym for the multiplication
        .registerOperator(new Operator("x", (a, b) -> a * b), 100);

    Scanner terminalInput = new Scanner(System.in);
    while (true) {
      System.out.println("Enter an equation (exit to terminate)");
      String equationString = terminalInput.nextLine();
      if ("exit".equals(equationString)) {
        break;
      }

      try {
        System.out.println(equationString + " = " + instance.calculate(equationString));
      } catch (EquationParserException e) {
        System.out.println(e.getMessage() + ": " + e.getEquation());
      }
      System.out.println("---------------------------------------------------------");
    }
  }
}
