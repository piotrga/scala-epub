package epub

import epub.dcmi.Metadata
import epub.dcmi.Metadata._

import java.io.{OutputStream, FileOutputStream}
import java.util.zip.{ZipOutputStream, ZipEntry}

object Serializer {

  private val dcmiNamespace = "http://purl.org/dc/elements/1.1/"

  private [epub] val containerDescriptorPath = "META-INF/conainer.xml"
  private [epub] val tocPath = "toc.ncx"
  private [epub] val contentDescriptorPath = "content.opf"
  val reservedPaths = Set(containerDescriptorPath, tocPath, contentDescriptorPath)

  def serialize(ePub: Publication, fileName: String): Unit =
    serialize(ePub, new FileOutputStream(fileName))

  def serialize(ePub: Publication,  out: OutputStream): Unit = {
    val zos = new ZipOutputStream(out)
    writeText("mimetype", Mimetypes.EPUB, zos)
    ePub.content.foreach( writePart(_, zos) )
    zos.close()
  }
/*
  private def xmlMetadata(ePub: Publication) = {
    var metadata = ePub.metadata
    if (! metadata.contains("identifier")) {
      metadata += ("identifier", Identifier(java.util.UUID.randomUUID()))
    }
  }
*/
  private def writeText(path: String, text: String,  out: ZipOutputStream) {
    val entry = new ZipEntry(path);
    out.putNextEntry(entry);
    out.write(text.getBytes)
  }

  private def writePart(part: PackagePart, out: ZipOutputStream) =
    writeText(part.path, part.content, out)
}

