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
    a  | b  | expected
    1  | 2  | 3
    1  | -2 | -1
    -1 | -2 | -3
    0  | 0  | 0
    //1.1 | 2.2 | 3.3
  }

  def "chain add numbers"() {
    expect:
    Equation.of(1).add(2).add(3).add(4).eq() == 10
  }

  @Unroll
  def "substract two numbers: #a - #b = #expected"() {
    expect:
    Equation.of(a).minus(b).eq() == expected

    where:
    a  | b  | expected
    0  | 0  | 0
    3  | 2  | 1
    1  | 2  | -1
    1  | -2 | 3
    -1 | -2 | 1
  }

  def "chain substract numbers"() {
    expect:
    Equation.of(15).minus(2).minus(3).minus(4).eq() == 6
  }

  @Unroll
  def "multiply two numbers: #a * #b = #expected"() {
    expect:
    Equation.of(a).multiply(b).eq() == expected

    where:
    a  | b  | expected
    0  | 0  | 0
    3  | 0  | 0
    3  | 1  | 3
    3  | -2 | -6
    -2 | -3 | 6
  }

  def "chain multiply numbers"() {
    expect:
    Equation.of(3).multiply(2).multiply(4).multiply(10).eq() == 240
  }
}
