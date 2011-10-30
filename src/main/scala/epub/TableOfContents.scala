package epub

class TOCEntry private [epub] (val label: String,
               val ref: String,
               val children: List[TOCEntry]) {

  def depth: Int = if (children.isEmpty) 1
  else 1 + children.map(_.depth).max
}

class TableOfContents private (val entries: List[TOCEntry]) {
  def depth = entries.map(_.depth).max
}

object TableOfContents {

  def apply(navPoints: TOCEntry*) = {
    new TableOfContents(navPoints.toList)
  }

  def entry(label: String, ref: String, children: TOCEntry*) = {
    new TOCEntry(label, ref, children.toList)
  }
}

