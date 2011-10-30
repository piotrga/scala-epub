package epub

import org.specs2.mutable._

class ExtensionMimetypeResolverSpec extends Specification {

  "Extension mimetype resolver" should {
    "resolve mimetype of css files" in {
      ExtensionMimetypeResolver.resolve("test.css", "") must beEqualTo(Mimetypes.CSS)
    }

    "resolve mimetype of html files" in {
      ExtensionMimetypeResolver.resolve("dir/some.html", "") must beEqualTo(Mimetypes.XHTML)
    }

    "resolve mimetype of jpeg image" in {
      ExtensionMimetypeResolver.resolve("file.jpeg", "") must beEqualTo(Mimetypes.JPG)
    }
  }
}