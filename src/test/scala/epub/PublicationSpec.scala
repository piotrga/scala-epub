package epub

import epub.dcmi._
import epub.dcmi.Metadata._
import epub.css.Css._
import epub.Packaging._
import org.specs2.mutable._
import toc._
import TableOfContents._

class PublicationSpec extends Specification {
  "Publication" should {
    "be successfully created" in {
      object Pub extends Publication {

        val metadata = Metadata(
          id("urn:uuid:12345"),
          creator(System.getProperty("user.name")),
          Metadata.title("My First EPUB"),
          language("en"))

        val content = html("OPS/main.html",
          <html>
            <body>
              <h1>My First EPUB</h1>
              <p>Hello, World!</p>
            </body>
          </html>) ::
          stylesheet("test.css", ".h1" #> ("font-weight" := "bold")) :: Nil

        val toc = TableOfContents(entry("", ""))
      }
      Pub mustNotEqual null
    }
  }
}
