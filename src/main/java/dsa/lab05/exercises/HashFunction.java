package dsa.lab05.exercises;


import dsa.lib.DSAObject;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A randomly-chosen hash function from a universal family.
 */
public class HashFunction
  extends DSAObject
{

  /** An arbitrary prime > 2^31 - 1. */
  private static long LARGE_PRIME = 9_000_123_456_789_000_007L;
  // NOTE: We need a prime greater than size.
  // NOTE: Since size is an int, its maximum value is 2^31 - 1.
  // NOTE: For this to be larger, it must be a long.
  // NOTE: (This prime was chosen specially for this module!)


  /** A random value 1 <= a < LARGE_PRIME. */
  private long a;
  // NOTE: This is the factor we multiply by.


  /** A random value 0 <= b < LARGE_PRIME. */
  private long b;
  // NOTE: This is the offset we add.


  /** The number of possible hashes. */
  private int size;
  // NOTE: Hashes will be integers h where 0 <= h < size.


  /**
   * Construct a hash function, randomly choosing its parameters.
   *
   * @param size the range of hashes that {@link #hash(Object)} should return
   */
  public HashFunction(int size)
  {
    // NOTE: Size must be at least 1, otherwise no hashes would be possible.
    if (size < 1)
    {
      throw new IllegalArgumentException();
    }

    // NOTE: (Pseudo-)randomly choose a factor and an offset.
    // NOTE: We're not using ThreadLocalRandom because we particularly care
    //       about thread-safety, just because it has a nice nextLong() method.
    // NOTE: You could use java.util.Random, but it would take more than 1 line.
    this.a = ThreadLocalRandom.current().nextLong(1, LARGE_PRIME - 1);
    this.b = ThreadLocalRandom.current().nextLong(0, LARGE_PRIME - 1);

    this.size = size;
  }


  /**
   * Get the size of the range of hashes.
   *
   * @return the range of hashes that {@link #hash(Object)} should return
   */
  public int size()
  {
    return this.size;
  }


  /**
   * Hash the given object to a non-negative int less than {@link #size()}.
   *
   * @param object an object
   * @return a hash of that object
   */
  public int hash(Object object)
  {
    int initialHash = Objects.hashCode(object);

    return (int)(Math.floorMod((Math.floorMod(this.a * initialHash + this.b,
      LARGE_PRIME)), this.size));

    // NOTE: `initialHash` is `k` in the lecture slides.
    // NOTE: Use `this.a`, `this.b`, `HashFunction.LARGE_PRIME` and `this.size`.
    // NOTE: Use `Math.floorMod(x, y)` instead of `x % y` to avoid issues
    //       with negative `x`s.
    // NOTE: Cast the result from long to int.
  }


  //<editor-fold defaultstate="collapsed" desc="toString()">


  @Override
  public String toString(String indent)
  {
    return "(" + this.a + ", " + this.b + ", " + this.size + ")";
  }


  //</editor-fold>

}
