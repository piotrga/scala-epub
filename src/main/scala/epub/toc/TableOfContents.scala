package epub.toc

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

