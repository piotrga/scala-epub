package epub

import java.io.OutputStream
import epub.css.Css._

object Packaging {
  implicit val mimetypeResolver = ExtensionMimetypeResolver

  def text(path: String, content: String)(implicit resolver: MimetypeResolver) = {
    new TextPart(path, content, resolver.resolve(path, content))
  }

  def file(path: String, fileName: String)(implicit resolver: MimetypeResolver) = {
    new FilePart(path, fileName, "")
  }

  def URI(path: String, uri: java.net.URI)(implicit resolver: MimetypeResolver) = {
    new URIPart(path, uri, "")
  }

  def html(path: String, xmlNode: xml.Node) = {
    new TextPart(path, xmlNode.toString(), Mimetypes.XHTML)
  }

  def stylesheet(path: String, cssSeq: RuleSet*) = {
    new TextPart(path, cssSeq.mkString, Mimetypes.CSS)
  }

  abstract class PackagePart (val path: String, var mimetype: String) {
    def write(out: OutputStream)
  }
  case class FilePart(val path: String,
                      val fileName: String,
                      var mimetype: String) extends PackagePart(path, mimetype) {
    def write(out: OutputStream) {
      println("path")
    }
  }

  case class URIPart(val path: String,
                     val uri: java.net.URI,
                     var mimetype: String) extends PackagePart(path, mimetype) {
    def write(out: OutputStream) {
      println("uri")
    }
  }

  case class TextPart(val path: String,
                      val text: String,
                      var mimetype: String) extends PackagePart(path, mimetype) {
    def write(out: OutputStream) {
      out.write(text.getBytes)
    }
  }
}