package epub

import dcmi.Metadata._
import Packaging._

class Publication (
  private[epub] var metadata: Metadata,
  private[epub] var content: List[PackagePart]) {

  def += (part: PackagePart) {
    content = part :: content
  }

  def ++= (parts: List[PackagePart]) {
    content = parts ::: content
  }

  private [epub] def toc = Nil
}

object Publication {
  def apply(meta: Metadata, content: PackagePart*) =
    new Publication(meta, content.toList)
}
