package epub

import dcmi.Metadata._
import Packaging._

import java.io.{OutputStream, FileOutputStream}
import java.util.zip.{ZipOutputStream, ZipEntry}

object Serializer {

  private val dcmiNamespace = "http://purl.org/dc/elements/1.1/"

  private [epub] val containerDescriptorPath = "META-INF/container.xml"
  private [epub] val tocPath = "toc.ncx"
  private [epub] val contentDescriptorPath = "content.opf"
  val reservedPaths = Set(containerDescriptorPath, tocPath, contentDescriptorPath)

  def serialize(ePub: Publication, fileName: String) {
    serialize(ePub, new FileOutputStream(fileName))
  }

  def serialize(ePub: Publication,  out: OutputStream) {
    val to = new ZipOutputStream(out)
    writeText("mimetype", Mimetypes.EPUB, to)
    writeContainerInfo(to)
    ePub.content.foreach( writePart(_, to) )
    to.close()
  }

  private def writePart(part: PackagePart, to: ZipOutputStream) {
    to.putNextEntry(new ZipEntry(part.path))
    part.write(to)
  }

  private def writeContainerInfo(to: ZipOutputStream) {
    to.putNextEntry(new ZipEntry(containerDescriptorPath))
    to.write("""<?xml version="1.0"?>""".getBytes)
    to.write(
      <container version="1.0" xmlns="urn:oasis:names:tc:opendocument:xmlns:container">
        <rootfiles>
            <rootfile full-path={contentDescriptorPath}
                      media-type="application/oebps-package+xml"/>
        </rootfiles>
      </container>.toString().getBytes
    )
  }

  private def makeManifest(parts: List[PackagePart]) = {
    <manifest>
      {parts.map(p => <item id={p.path} href={p.path} media-type={p.mimetype}/>)}
    </manifest>
  }

  private def writeText(path: String, text: String,  to: ZipOutputStream) {
    to.putNextEntry(new ZipEntry(path))
    to.write(text.getBytes)
  }
}

