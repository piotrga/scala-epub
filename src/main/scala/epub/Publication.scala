package epub

import dcmi.Metadata._
import toc._
import Packaging._

abstract class Publication {
  def metadata: Metadata
  def content: List[PackagePart]
  def toc: TableOfContents
}
