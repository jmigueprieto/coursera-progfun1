//"testxexxxwwTAa".groupBy(c => c.toLower).map(e => (e._1, e._2.length)).toList.sortBy(_._1)

type Occurrences = List[(Char, Int)]
val possibleHeadValues: List[Occurrences] = List(List(('a', 1)), List(('a', 2)))
val possibleTailValues: List[Occurrences] = List(List(('b', 1)), List(('b', 2)), List(('c', 1)))
val all /*: List[Occurrences]*/ = for {
  h <- possibleHeadValues
  t <- possibleTailValues
} yield h ++ t

all.filter(o => o.map(x => x._2).sum == 4)

/*
 val length = sentence.reduce((x, y) => x.length + y.length)
      val validCombinations = combinations()
        .filter(o => o.map(x => x._2).sum == length)
        //.filter(o => dictionaryByOccurrences.contains(o))
 */