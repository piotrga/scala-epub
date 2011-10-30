package epub

import Packaging._

abstract class MimetypeResolver {
  def resolve(part: PackagePart): String

  class NoExtensionException extends RuntimeException
  class UnknownExtensionException extends RuntimeException
}


object ExtensionMimetypeResolver extends MimetypeResolver {

  import Mimetypes._

  private val extensionMimetypes =
    Map("html" -> XHTML, "xml" -> XML, "ncx" -> XML,
      "css" -> CSS, "png" -> PNG, "jpg" -> JPG, "jpeg" -> JPG,
      "svg" -> SVG)

  override def resolve(part: PackagePart) = {
    val path = part.path
    val extension = path.lastIndexOf('.') match {
      case -1 => throw new NoExtensionException
      case x => path.substring(x + 1).toLowerCase
    }
    
    val result = extensionMimetypes get extension
    result match {
      case None => throw new UnknownExtensionException
      case Some(ext) => ext
    }
  }
}
