package epub

import epub.css.Css._
import java.lang.IllegalArgumentException

class PackagePart private [epub] (
  val path: String,
  val content: String,
  val mimetype: String
)

object PackagePart {
  implicit val mimetypeResolver = ExtensionMimetypeResolver

  def text(path: String, content: String)(implicit resolver: MimetypeResolver) = {
    validatePath(path)
    new PackagePart(path, content, resolver.resolve(path, content))
  }

  def file(path: String, fileName: String)(implicit resolver: MimetypeResolver) = {
    validatePath(path)
    val contents = io.Source.fromFile(fileName).mkString
    new PackagePart(path, contents, resolver.resolve(path, contents))
  }

  def URI(path: String, uri: java.net.URI)(implicit resolver: MimetypeResolver) = {
    validatePath(path)
    val contents = io.Source.fromFile(uri).mkString
    new PackagePart(path, contents, resolver.resolve(path, contents))
  }

  def html(path: String, xmlNode: xml.Node) = {
    validatePath(path)
    new PackagePart(path, xmlNode.toString, Mimetypes.XHTML)
  }

  def stylesheet(path: String, cssSeq: RuleSet*) = {
    validatePath(path)
    val contents = cssSeq.mkString
    new PackagePart(path, contents, Mimetypes.CSS)
  }

  private def validatePath(path: String) =
    if (Serializer.reservedPaths contains path)
      throw new IllegalArgumentException("Given path is reserved for system needs. Please choose another.")
}
