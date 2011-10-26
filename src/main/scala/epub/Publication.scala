package epub

import epub.dcmi.Metadata

class Publication (
  private[epub] var metadata: Map[String, Metadata],
  private[epub] var content: List[PackagePart]) {

  def += (part: PackagePart) = {
    content = part :: content
  }

  def ++= (parts: List[PackagePart]) = {
    content = parts ::: content
  }

  private [epub] def toc = Nil
}

object Publication {
  def apply(meta: Map[String, Metadata], content: PackagePart*) =
    new Publication(meta, content.toList)
}
