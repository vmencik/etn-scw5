package cz.etn.scw5

object Implicits {
  implicit class AndFun[A](val left: A => Boolean) extends AnyVal {

    def &&(right: A => Boolean): A => Boolean = (a: A) => left(a) && right(a)

  }
}