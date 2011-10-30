package epub

import epub.css.Css._
import java.io.{InputStream, IOException, FileInputStream, OutputStream}

object Packaging {
  implicit val mimetypeResolver = ExtensionMimetypeResolver

  implicit def strToResolver(mimetype: String) = new MimetypeResolver {
    def resolve(part: PackagePart): String = mimetype
  }

  def text(path: String, content: String)(implicit resolver: MimetypeResolver) = {
    withMimetypeResolution(new TextPart(path, content, ""))
  }

  def file(path: String, fileName: String)(implicit resolver: MimetypeResolver) = {
    withMimetypeResolution(new FilePart(path, fileName, ""))
  }

  def URI(path: String, url: java.net.URL)(implicit resolver: MimetypeResolver) = {
    withMimetypeResolution(new URIPart(path, url, ""))
  }

  def html(path: String, xmlNode: xml.Node) = {
    new TextPart(path, xmlNode.toString(), Mimetypes.XHTML)
  }

  def stylesheet(path: String, cssSeq: RuleSet*) = {
    new TextPart(path, cssSeq.mkString, Mimetypes.CSS)
  }

  abstract class PackagePart {
    val path: String
    private [epub] var mimetype: String
    def write(to: OutputStream)
  }

  case class FilePart(override val path: String,
                      fileName: String,
                      override var mimetype: String) extends PackagePart {
    def write(to: OutputStream) {
      copyFromStream(new FileInputStream(fileName), to)
    }
  }

  case class URIPart(override val path: String,
                     url: java.net.URL,
                     override var mimetype: String) extends PackagePart {
    def write(to: OutputStream) {
      copyFromStream(url.openStream(), to)
    }
  }

  case class TextPart(override val path: String,
                      val text: String,
                      override var mimetype: String) extends PackagePart {
    def write(to: OutputStream) {
      to.write(text.getBytes)
    }
  }

  private def withMimetypeResolution(part: PackagePart)(implicit resolver: MimetypeResolver) = {
    part.mimetype = resolver.resolve(part)
    part
  }

  private def copyFromStream(from: InputStream, to: OutputStream) {
    try {
      val bufSize = 4096
      val buffer = new Array[Byte](bufSize)
      Iterator.continually(from.read(buffer))
        .takeWhile(_ != -1)
        .foreach { to.write(buffer, 0 , _) }
    } finally {
      from.close()
    }
  }
}
