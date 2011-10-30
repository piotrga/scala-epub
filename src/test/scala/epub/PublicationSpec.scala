package epub

import epub.dcmi._
import epub.dcmi.Metadata._
import epub.css.Css._
import epub.Packaging._
import org.specs2.mutable._

class PublicationSpec extends Specification {
  "Publication" should {
    "be successfully created" in {
      Publication(
        Metadata(
          id("urn:uuid:12345"),
          creator(System.getProperty("user.name")),
          Metadata.title("My First EPUB"),
          language("en")),
        html("OPS/main.html",
          <html>
            <body>
              <h1>My First EPUB</h1>
              <p>Hello, World!</p>
            </body>
          </html>),
        stylesheet("test.css", ".h1" #> ("font-weight" := "bold"))
      ) mustNotEqual null
    }
  }
}
