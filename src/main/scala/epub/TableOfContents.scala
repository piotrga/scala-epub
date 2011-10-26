package epub

class TOCEntry private [epub] (val label: String,
               val ref: String,
               var playOrder: Int,
               val children: List[TOCEntry]) {
  def calculateOrder(parentOrder: Int): Int = {
    playOrder = parentOrder + 1
    var lastOrder = playOrder
    for (child <- children) {
      lastOrder = child.calculateOrder(lastOrder)
    }
    lastOrder
  }

  def depth(): Int = if (children.isEmpty) 1
  else 1 + children.map(_.depth()).max
}

class TableOfContents private (val depth: Int, val entries: List[TOCEntry])

object TableOfContents {

  def apply(navPoints: TOCEntry*) = {
    val points = navPoints.toList
    val depth = points.map(_.depth()).max
    var order = 0

    points.foreach(p => order = p.calculateOrder(order))
    new TableOfContents(depth, points)
  }

  def entry(label: String, ref: String, children: TOCEntry*) = {
    new TOCEntry(label, ref, 0, children.toList)
  }
}

