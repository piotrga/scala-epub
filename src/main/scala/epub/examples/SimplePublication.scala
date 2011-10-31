package epub.examples

import epub._
import epub.css.Css._
import epub.dcmi._
import epub.dcmi.Metadata._
import epub.Packaging._
import toc._
import toc.TableOfContents._

object SimplePublication {
  def main(args: Array[String]) {
    object ePub extends Publication {
      val metadata = Metadata(
        id("urn:uuid:12345"),
        creator(System.getProperty("user.name")),
        title("My First EPUB"),
        language("en"))

      val content =
        html("OPS/main.html",
          <html><head><title>My First EPUB</title></head>
            <body>
              <h1>My First EPUB</h1>
              <p>Hello, World!</p>
            </body>
          </html>) ::
        stylesheet("test.css",
          "h1" #> (
            "color" := "gray",
            "border-bottom" := "2px solid gray",
            "text-align" := "right",
            "margin" := "2em 8px 1em 0px"),
          "p" #> (
            "margin" := "0px",
            "text-indent" := "1em",
            "text-align" := "justify")) :: Nil

      val toc = TableOfContents(entry("Main text", "OPS/main.html"))
    }

    Serializer.serialize(ePub, "simple.epub")
  }
}
