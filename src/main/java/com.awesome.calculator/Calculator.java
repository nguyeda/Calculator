package com.awesome.calculator;

import java.util.Date;
import java.util.Scanner;

public class Calculator {
  public static void main(String... args) {
    EquationParser instance = new EquationParser()
        // register a synonym for the multiplication
        .registerOperator(new Operator("x", (a, b) -> a * b), 100);

    Scanner terminalInput = new Scanner(System.in);
    while (true) {
      System.out.println("Enter an equation (exit to terminate):");
      String equationString = terminalInput.nextLine();
      if ("exit".equals(equationString)) {
        break;
      }

      try {
        long start = new Date().getTime();
        double res = instance.calculate(equationString);
        long duration = new Date().getTime() - start;

        System.out.println(equationString + " = " + instance.calculate(equationString) + " (took " + duration + "ms)");
      } catch (EquationParserException e) {
        System.out.println(e.getMessage() + ": " + e.getEquation());
      }
      System.out.println("---------------------------------------------------------");
    }
  }
}
