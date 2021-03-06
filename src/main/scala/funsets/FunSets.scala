package funsets


/**
  * 2. Purely Functional Sets.
  */
object FunSets {
  /**
    * We represent a set by its characteristic function, i.e.
    * its `contains` predicate.
    */
  type Set = Int => Boolean


  def boolOps(f:(Boolean, Boolean) => Boolean)(s: Set, t: Set): Int => Boolean = {
    x:Int => f(contains(s,x),contains(t, x))
  }


  /**
    * Indicates whether a set contains a given element.
    */
  def contains(s: Set, elem: Int): Boolean = s(elem)

  /**
    * Returns the set of the one given element.
    */
  def singletonSet(elem: Int): Set = {
    def inside(x: Int): Boolean = elem == x
    inside
  }


  /**
    * Returns the union of the two given sets,
    * the sets of all elements that are in either `s` or `t`.
    */
  def union(s: Set, t: Set): Set = boolOps(_||_)(s,t)

  /**
    * Returns the intersection of the two given sets,
    * the set of all elements that are both in `s` and `t`.
    */
  def intersect(s: Set, t: Set): Set = boolOps(_&&_)(s,t)

  /**
    * Returns the difference of the two given sets,
    * the set of all elements of `s` that are not in `t`.
    */
  def diff(s: Set, t: Set): Set = {
    def inside(x: Int):Boolean = {
      contains(s,x) && !contains(t,x)
    }
    inside
  }


  /**
    * Returns the subset of `s` for which `p` holds.
    */
  def filter(s: Set, p: Int => Boolean): Set = intersect(s,p)


  /**
    * The bounds for `forall` and `exists` are +/- 1000.
    */
  val bound = 1000

  /**
    * Returns whether all bounded integers within `s` satisfy `p`.
    */
  def forall(s: Set, p: Int => Boolean): Boolean = {

    def find(c: Seq[Int]):Boolean = c match {
      case Nil => true
      case h :: t => if (p(h)) find(t) else false
    }
    val nums = collect(s)
    if (nums.isEmpty) false
    else find(nums)
  }

  def collect(f: Int => Boolean): Seq[Int] = {
    List.range(-bound-1, bound+1).filter(x => f(x))
  }

  /**
    * Returns whether there exists a bounded integer within `s`
    * that satisfies `p`.
    */
  def exists(s: Set, p: Int => Boolean): Boolean = {
    forall(intersect(s,p), p)
  }

  /**
    * Returns a set transformed by applying `f` to each element of `s`.
    */
  def map(s: Set, f: Int => Int): Set = {
    def make(c: Seq[Int]): Set = c match {
      case h :: t if (t==Nil) => singletonSet(f(h))
      case h :: t => union(singletonSet(f(h)), make(t))
    }
    make(collect(s))
  }

  /**
    * Displays the contents of a set
    */
  def toString(s: Set): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }

  /**
    * Prints the contents of a set on the console.
    */
  def printSet(s: Set) {
    println(toString(s))
  }
}
