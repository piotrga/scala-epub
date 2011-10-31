package epub

import Packaging._

abstract class MimetypeResolver {
  def resolve(part: PackagePart): String
}

object ExtensionMimetypeResolver extends MimetypeResolver {

  import Mimetypes._

  private val extensionMimetypes =
    Map(
      "html" -> XHTML, "xhtml"-> XHTML,
      "xml" -> XML, "ncx" -> XML,
      "css" -> CSS, "png" -> PNG,
      "jpg" -> JPG, "jpeg" -> JPG,
      "svg" -> SVG, "ncx" -> NCX)

  override def resolve(part: PackagePart) = {
    val path = part.path

    val result = extensionMimetypes get extension(path)
    result match {
      case None => throw new UnknownExtensionException
      case Some(ext) => ext
    }
  }

  private [epub] def extension(path: String) = path.lastIndexOf('.') match {
    case -1 => throw new NoExtensionException
    case x => path.substring(x + 1).toLowerCase
  }

  class NoExtensionException extends RuntimeException
  class UnknownExtensionException extends RuntimeException
}
