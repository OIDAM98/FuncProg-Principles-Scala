package recfun

object RecFun extends RecFunInterface {

  def main(args: Array[String]): Unit = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(s"${pascal(col, row)} ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int =
    if (c == 0 || c == r) 1 // Asking for beginning of row or end
    else
      pascal(c - 1, r - 1) + // Calculate upper left
        pascal(c, r - 1) // Calculate upper right

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {
    def aux(parenthesis: List[Char], str: List[Char]): Boolean =
      str match {
        case head :: next =>
          if (head == '(') aux(head :: parenthesis, next) // Add ( to list
          else if (head == ')') {
            if (parenthesis.isEmpty) false // Found ending ) but there are no (
            else aux(parenthesis.tail, next) // There is an (
          } else aux(parenthesis, next) // Char other than ()
        case Nil => parenthesis.isEmpty // Only balanced if list of ( is empty
      }
    aux(Nil, chars)
  }

  /**
    * Exercise 3
    */
  def countChange(money: Int, coins: List[Int]): Int = {
    def aux(rem: Int, change: List[Int]): Int = {
      if (rem == 0) 1 // Ways to give 0 money is 1 (0)
      else if (rem < 0) 0 // No coins can be given to negative money
      else
        change match {
          case Nil => 0 // No coins to be given despite money wanted
          case head :: next =>
            aux(rem - head, change) + // Calculate the ways if head is given
              aux(rem, next) // Calculate the ways without giving the head
        }
    }
    aux(money, coins)

  }
}
