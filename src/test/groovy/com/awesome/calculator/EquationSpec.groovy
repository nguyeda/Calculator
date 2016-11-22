package com.awesome.calculator

import spock.lang.Specification
import spock.lang.Unroll

class EquationSpec extends Specification {

  @Unroll
  def "#equation = #expected"() {
    expect:
    new Equation().calculate(equation) == expected

    where:
    equation        | expected
    '0 + 0'         | 0
    '1 + 2'         | 3
    '-6 + 2'        | -4
    '-6 + -2'       | -8
    '1 + 2 + 3 + 4' | 10
  }
}
