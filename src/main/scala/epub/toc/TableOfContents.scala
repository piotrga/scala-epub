
 package epub.toc
 
class TOCEntry private [epub] (val label: String,
               val ref: String,
               val children: List[TOCEntry]) {

  def depth: Int = if (children.isEmpty) 1
  else 1 + children.map(_.depth).max
}

 class TableOfContents private (val entries: List[TOCEntry]) {
   def depth = entries.map(_.depth).max
 }
