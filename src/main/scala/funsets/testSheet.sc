
//object intsets {
//  println("I'm here and there")
//  val x = new NonEmpty(22, new Empty, new Empty)
//
//}


//abstract class IntSet {
//
//  def incl(x: Int): IntSet
//  def contains(x: Int):Boolean
//  def union(other: IntSet): IntSet
//}
//class Empty extends IntSet {
//  def contains(x: Int): Boolean = false
//  def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
//  def union(other: IntSet): IntSet = other
//  override def toString = "."
//}
//
//class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends  IntSet{
//  def contains(x: Int): Boolean = {
//    if (x<elem) left contains x
//    else if (x>elem) right contains x
//    else true
//  }
//  def incl(x: Int): IntSet = {
//    if (x<elem) new NonEmpty(elem, left incl  x, right)
//    else if (x>elem) new NonEmpty(elem, left, right incl x)
//    else this
//  }
//  def union(other: IntSet): IntSet = other match {
//    case t: NonEmpty if (t.elem < this.elem) => this.left.union(t)
//    case t: NonEmpty if (t.elem > this.elem) => this.right.union(t)
//    case _ => this
//  }
//  override def toString = "{" + left + elem + right + "}"
//}

def nth[T](x: List[T], c: Int):T = x match {
  case _ if (c<0 || x.isEmpty) => throw new IndexOutOfBoundsException
  case h :: t if (c==0) => h
  case h :: t => nth(t, c-1)
}

nth(List(1,2,34,44,22), 5)
