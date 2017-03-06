package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
   test("string take") {
     val message = "hello, world"
     assert(message.take(5) == "hello")
   }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val s22 = singletonSet(2)
    val sn = union(union(union(s1, s2), s3), s4)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      val x = union(s, s3)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
      assert(contains(x, 3))
    }
  }

  test("intersection contains only values for values in all sets") {
    new TestSets {
      val s = intersect(s1, s2)
      val sd = intersect(s2, s22)
      assert(!contains(s, 1))
      assert(contains(sd, 2))
    }
  }

  test("difference contains only values which not included in both sides.") {
    new TestSets {
      val x1 = singletonSet(1)
      val x3 = singletonSet(3)
      val x4 = singletonSet(4)
      val x5 = singletonSet(5)
      val x6 = singletonSet(6)
      val x1000 = singletonSet(1000)
      val xn = union(union(union(union(union(x1, x3), x4), x5), x6), x1000)
      val x0 = singletonSet(0)
      val x1000neg = singletonSet(-1000)
      val xr = union(x0, x1000neg)
      val d = diff(sn, xn)
      val r = diff(xr, sn)

      assert(contains(d, 2))
      assert(!contains(d, 1000))
      assert(contains(r, -1000))
    }
  }

  test("filter contains only values which belongs to the filter.") {
    new TestSets {
      val sf1 = filter(sn, s2)
      val sf2 = filter(sn, s1)
      assert(contains(sf1, 2))
      assert(!contains(sf2, 2))
    }
  }

  test("All values of test set will be found for forAll.") {
    new TestSets {
      val s5 = singletonSet(5)
      assert(forall(sn, x => x < 1000))
      assert(!forall(sn, x => (x==5)))
    }
  }

  test("At least one value of test set will be found.") {
    new TestSets {
      val s5 = singletonSet(5)
      assert(!exists(sn, s5))
      assert(exists(sn, s2))
    }
  }

  test("All values of set will be changed by function.") {
    new TestSets {
      val t = singletonSet(1000)
      val sd = union(union(s1, s2),t)

      val sx = map(sd, x => x-2)
      assert(contains(sx, -1))
      assert(contains(sx, 0))
      assert(contains(sx, 998))
    }
  }


}
