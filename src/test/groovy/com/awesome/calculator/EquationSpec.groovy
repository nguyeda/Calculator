package com.awesome.calculator

import spock.lang.Specification
import spock.lang.Unroll

class EquationSpec extends Specification {

  @Unroll
  def "default operators: #equation = #expected"() {
    expect:
    new Equation().calculate(equation) == expected

    where:
    equation            | expected
    '0 + 0'             | 0
    '1 + 0'             | 1
    '1 + 2'             | 3
    '2 + 1'             | 3
    '-6 + 2'            | -4
    '-6 + -2'           | -8
    '1 + 2 + 3 + 4'     | 10
    '3-1'               | 2
    '1-0'               | 1
    '3-1 + 4'           | 6
    '3 - 1 - 4'         | -2
    '2 * 4'             | 8
    '4 * 2'             | 8
    '2 * 0'             | 0
    '-1 * 2'            | -2
    '3*2+1'             | 7
    '3*2+1-3'           | 4
    '1 +2 * 3'          | 7
    '4/2'               | 2
    '-4/2'              | -2
    '4/-2'              | -2
    '-4/-2'             | 2
    '0/5'               | 0
    '3 * 4 / 2'         | 6
    '4/2 * 3'           | 6
    '1 + 3 * 2 - 8 / 4' | 5
  }

  @Unroll
  def "custom operator modulo: #equation = #expected"() {
    given:
    def operator = new Operator('modulo', { a, b -> a % b })

    expect:
    new Equation()
        .registerOperator(operator, 100)
        .calculate(equation) == expected

    where:
    equation      | expected
    '17 modulo 3' | 2
    '17modulo3'   | 2
  }
}
