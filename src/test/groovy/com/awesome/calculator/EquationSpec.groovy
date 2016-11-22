package groovy.com.awesome.calculator

import com.awesome.calculator.Equation
import spock.lang.Specification
import spock.lang.Unroll

class EquationSpec extends Specification {

  def "noop"() {
    expect:
    new Equation(1).eq() == 1
  }

  @Unroll
  def "add two numbers: #a + #b = #expected"() {
    expect:
    new Equation(a).add(b).eq() == expected

    where:
    a  | b  | expected
    1  | 2  | 3
    1  | -2 | -1
    -1 | -2 | -3
    0  | 0  | 0
  }

  def "chain add numbers"() {
    expect:
    new Equation(1).add(2).add(3).add(4).eq() == 10
  }

  @Unroll
  def "substract two numbers: #a - #b = #expected"() {
    expect:
    new Equation(a).minus(b).eq() == expected

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
    new Equation(15).minus(2).minus(3).minus(4).eq() == 6
  }

  @Unroll
  def "multiply two numbers: #a * #b = #expected"() {
    expect:
    new Equation(a).multiply(b).eq() == expected

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
    new Equation(3).multiply(2).multiply(4).multiply(10).eq() == 240
  }

  @Unroll
  def "divide two numbers: #a / #b = #expected"() {
    expect:
    new Equation(a).divide(b).eq() == expected

    where:
    a  | b  | expected
    0  | 1  | 0
    3  | 1  | 3
    3  | -3 | -1
    -6 | -2 | 3
  }

  def "attempt to divide by 0 throws an exception"() {
    when:
    new Equation(100).divide(10).divide(0)

    then:
    def exception = thrown(RuntimeException)
    exception.message == 'Attempt to divide by 0'
  }

  def "chain divide numbers"() {
    expect:
    new Equation(100).divide(10).divide(2).divide(5).eq() == 1
  }

  def "custom operator: modulo"() {
    expect:
    new Equation(17).operation(6, { a, b -> a % b }).eq() == 5
  }

  def "custom operator: negate"() {
    expect:
    new Equation(6).operation({ a -> -a }).eq() == -6;
  }
}
