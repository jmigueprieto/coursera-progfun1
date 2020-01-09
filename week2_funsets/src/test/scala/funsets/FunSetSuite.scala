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
    * val s1 = singletonSet(1)
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
    val s5 = singletonSet(5)
    val s6 = singletonSet(6)
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
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("test filter") {
    new TestSets {
      val s = union(union(union(s1, s2), s3), s4)
      assert(contains(s, 1))
      assert(contains(s, 2))
      assert(contains(s, 3))
      assert(contains(s, 4))
      val even = filter(s, x => x % 2 == 0)
      assert(!contains(even, 1))
      assert(contains(even, 2))
      assert(!contains(even, 3))
      assert(contains(even, 4))
    }
  }

  test("test diff") {
    new TestSets {
      val s = union(union(union(s1, s2), s3), s4)
      val even = filter(s, x => x % 2 == 0)
      val odd = diff(s, even)
      assert(contains(odd, 1))
      assert(!contains(odd, 2))
      assert(contains(odd, 3))
      assert(!contains(odd, 4))
    }
  }

  test("test forall") {
    new TestSets {
      val s = union(union(union(s1, s2), s3), s4)
      assert(forall(s, x => x > 0))
      assert(forall(s, x => x < 5))
      assert(!forall(s, x => x > 1))
      assert(!forall(s, x => x < 4))
    }
  }

  test("test exists") {
    new TestSets {
      val s = union(union(union(s1, s2), s3), s4)
      assert(exists(s, x => x == 3))
      assert(exists(s, x => x == 1))
      assert(!exists(s, x => x > 4))
      assert(!exists(s, x => x == 0))
    }
  }

  test("test map") {
    new TestSets {
      val s = union(union(union(s1, s2), s3), s4)
      val squares = map(s, x => x * x) //1, 4, 9, 16
      assert(!contains(squares, 2))
      assert(!contains(squares, 3))
      assert(contains(squares, 1))
      assert(contains(squares, 4))
      assert(contains(squares, 9))
      assert(contains(squares, 16))
    }
  }


}
