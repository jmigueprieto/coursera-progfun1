package patmat

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import patmat.Huffman._


@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {

  trait TestTrees {
    val t1 = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
    val t2 = Fork(Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5), Leaf('d', 4), List('a', 'b', 'd'), 9)
  }


  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }


  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a', 'b', 'd'))
    }
  }


  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }


  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 3)))
  }


  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))
  }


  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("times returns correct count") {
    assert(times(List('a', 'b', 'a')) === ('a', 2) :: ('b', 1) :: List())
    assert(times(List('a', 'b', 'a', 'x', 'a')) === ('a', 3) :: ('b', 1) :: ('x', 1) :: List())
    assert(times(List('a', 'b', 'a', 'x', 'a', 'x', 'y')) === ('a', 3) :: ('b', 1) :: ('x', 2) :: ('y', 1) :: List())
  }

  test("makeOrderedLeafList returns sorted list of Leaf") {
    val res = makeOrderedLeafList(('a', 3) :: ('b', 1) :: ('x', 2) :: ('y', 1) :: List())
    assert(res === Leaf('b', 1) :: Leaf('y', 1) :: Leaf('x', 2) :: Leaf('a', 3) :: List())
  }

  test("combine returns combined and sorted list of Trees") {
    val list1 = Leaf('b', 1) :: Leaf('y', 1) :: Leaf('x', 3) :: Leaf('a', 4) :: List()
    val list2 = combine(list1)
    assert(list2 ===
      Fork(Leaf('b', 1), Leaf('y', 1), List('b', 'y'), 2) ::
        Leaf('x', 3) ::
        Leaf('a', 4) ::
        List())

    val list3 = combine(list2)
    assert(list3 ===
      Leaf('a', 4) ::
        Fork(Fork(Leaf('b', 1), Leaf('y', 1), List('b', 'y'), 2), Leaf('x', 3), List('b', 'y', 'x'), 5) ::
        List())

    val list4 = combine(list3)
    assert(list4 ===
      Fork(Leaf('a', 4),
        Fork(Fork(Leaf('b', 1), Leaf('y', 1), List('b', 'y'), 2), Leaf('x', 3), List('b', 'y', 'x'), 5),
        List('a', 'b', 'y', 'x'), 9) :: List())
  }

  test("createCodeTree returns the tree") {
    val list = createCodeTree(string2Chars("xxxbyaaaa"))
    assert(list ===
      Fork(Leaf('a', 4),
        Fork(Fork(Leaf('b', 1), Leaf('y', 1), List('b', 'y'), 2), Leaf('x', 3), List('b', 'y', 'x'), 5),
        List('a', 'b', 'y', 'x'), 9))
  }

  test("codeBits returns the correct representation of the chars") {
    val codeTable = ('a', List(0, 0)) ::
      ('b', List(0, 1)) ::
      ('c', List(1, 0)) ::
      ('d', List(1, 1)) ::
      ('e', List(1, 0, 0)) ::
      List()

    def encoder = codeBits(codeTable)(_)

    assert(List(1, 0, 0) == encoder('e'))
    assert(List(0, 1) == encoder('b'))
    assert(List(0, 0) == encoder('a'))
  }


  test("convert returns a correct table") {
    val table1 = convert(createCodeTree(string2Chars("xxxbyaaaa")))
    assert(table1 ==
      List(
        ('a', List(0)),
        ('x', List(1, 1)),
        ('y', List(1, 0, 1)),
        ('b', List(1, 0, 0))
      )
    )

    val table2 = convert(createCodeTree(string2Chars("xxxbyyaaaacz")))
    assert(table2 ==
      List(
        ('a', List(1, 1)),
        ('x', List(1, 0)),
        ('y', List(0, 0)),
        ('z', List(0, 1, 0)),
        ('c', List(0, 1, 1, 1)),
        ('b', List(0, 1, 1, 0))
      )
    )
  }

  test("quickEncodes returns the same value as encode") {
    val text = string2Chars("huffmanestcool")
    assert(secret == encode(frenchCode)(text))
    assert(encode(frenchCode)(text) ==
      quickEncode(frenchCode)(text))
  }

  test("combine of a singleton or a Nil") {
    combine(List(Leaf('c', 1)))
    combine(List())
  }
}
