package groovy.com.awesome.calculator

import com.awesome.calculator.Equation
import spock.lang.Specification
import spock.lang.Unroll

class EquationSpec extends Specification {

  def "noop"() {
    expect:
    Equation.of(1).eq() == 1
  }

  @Unroll
  def "add two numbers: #a + #b = #expected"() {
    expect:
    Equation.of(a).add(b).eq() == expected

    where:
    a   | b   | expected
    1   | 2   | 3
    1   | -2  | -1
    -1  | -2  | -3
    0   | 0   | 0
    //1.1 | 2.2 | 3.3
  }

}
