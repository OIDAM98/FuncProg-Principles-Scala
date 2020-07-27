package funsets

import org.junit._

/**
  * This class is a test suite for the methods in object FunSets.
  *
  * To run this test suite, start "sbt" then run the "test" command.
  */
class FunSetSuite {

  import FunSets._

  @Test def `contains is implemented`: Unit = {
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
    */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
    * This test is currently disabled (by using @Ignore) because the method
    * "singletonSet" is not yet implemented and the test would fail.
    *
    * Once you finish your implementation of "singletonSet", remvoe the
    * @Ignore annotation.
    */
  @Test def `singleton set one contains one`: Unit = {

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

  @Test def `union contains all elements of each set`: Unit = {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  @Test def `intersection contains no element`: Unit = {
    new TestSets {
      val s = intersect(intersect(s1, s2), s3)
      assert(!contains(s, 1), "Intersection 1")
      assert(!contains(s, 2), "Intersection 2")
      assert(!contains(s, 3), "Intersection 3")
    }
  }

  @Test def `diff contains a single element`: Unit = {
    new TestSets {
      val s = diff(union(s1, s2), s2)
      assert(contains(s, 1), "Diff 1")
      assert(!contains(s, 2), "Diff 2")
      assert(!contains(s, 3), "Diff 3")
    }
  }

  @Test def `filter contains no elements`: Unit = {
    new TestSets {
      val s = union(union(s1, s2), s3)
      val filtered = filter(s, x => x > 4)
      assert(!contains(filtered, 1), "Filter 1")
      assert(!contains(filtered, 2), "Filter 2")
      assert(!contains(filtered, 3), "Filter 3")
    }
  }

  @Test def `forall asserts correctly`: Unit = {
    new TestSets {
      val s = union(union(s1, s2), s3)
      assert(forall(s, x => x < 4))
      assert(forall(s, x => x > 0))
      assert(!forall(s, x => x < 1))
      assert(!forall(s, x => x % 2 == 0))
    }
  }

  @Test def `exists works`: Unit = {
    new TestSets {
      val s = union(union(s1, s2), s3)
      assert(exists(s, x => x < 4))
      assert(!exists(s, x => x > 4))
      assert(!exists(s, x => x == 0))
    }
  }

  @Test def `mapping over a set works correctly`: Unit = {
    new TestSets {
      val s = union(union(s1, s2), s3)
      val mapped = map(s, elem => elem + 10)
      assert(exists(mapped, x => x > 10), "Values larger than 10")
      assert(contains(mapped, 11), "map 11")
      assert(contains(mapped, 12), "map 12")
      assert(contains(mapped, 13), "map 13")
      assert(!contains(mapped, 1), "map no 1")
    }
  }

  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
