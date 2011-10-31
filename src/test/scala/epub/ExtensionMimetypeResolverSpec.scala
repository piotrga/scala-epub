package epub

import org.specs2.mutable._
import Packaging._
import ExtensionMimetypeResolver._

class ExtensionMimetypeResolverSpec extends Specification {

  "Extension mimetype resolver" should {

    "extract extension from simple file name" in {
      ExtensionMimetypeResolver.extension("file.ext") must beEqualTo("ext")
    }

    "extract extension from file in directory"in {
      ExtensionMimetypeResolver.extension("Some/path/to/file.txt") must beEqualTo("txt")
    }

    "throw appropriate exception if extension is not specified"in {
      ExtensionMimetypeResolver.extension("noExtension") must throwA[NoExtensionException]
    }

    "resolve mimetype of css files" in {
      ExtensionMimetypeResolver.resolve(FilePart("test.css", "", "")) must beEqualTo(Mimetypes.CSS)
    }

    "resolve mimetype of xhtml files" in {
      ExtensionMimetypeResolver.resolve(FilePart("test.xhtml", "", "")) must beEqualTo(Mimetypes.XHTML)
    }

    "resolve mimetype of jpeg image" in {
      ExtensionMimetypeResolver.resolve(FilePart("file.jpeg", "", "")) must beEqualTo(Mimetypes.JPG)
    }

    "throw appropriate exception if unknown exception is given" in {
      ExtensionMimetypeResolver.resolve(FilePart("unknown.extension", "", "")) must throwAn[UnknownExtensionException]
    }
  }
}