package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = if (c == 0 || c == r)
    1
  else
    pascal(c - 1, r - 1) + pascal(c, r - 1)

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {
    def loop(chars: List[Char], openCount: Int): Boolean = {
      if (chars.isEmpty) {
        openCount == 0
      } else {
        val c = chars.head
        val newOpenCount = if (c == '(') {
          openCount + 1
        } else if (c == ')') {
          openCount - 1
        } else {
          openCount
        }

        openCount >= 0 && loop(chars.tail, newOpenCount)
      }
    }

    loop(chars, 0)
  }

  /**
    * Exercise 3
    */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money > 0 && coins.nonEmpty)
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    else if (money == 0)
      1
    else
      0
  }
}
