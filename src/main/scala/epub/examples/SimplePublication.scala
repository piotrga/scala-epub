package epub.examples

import epub._
import epub.css.Css._
import epub.dcmi._
import epub.dcmi.Metadata._
import epub.Packaging._

object SimplePublication {
  def main(args: Array[String]) = {
    val ePub = Publication(
        Metadata(
          id("urn:uuid:12345"),
          creator(System.getProperty("user.name")),
          title("My First EPUB"),
          language("en")),
        html("OPS/main.html",
          <html>
            <body>
              <h1>My First EPUB</h1>
              <p>Hello, World!</p>
            </body>
          </html>),
        stylesheet("test.css",
          "h1" #> (
            "color" := "gray",
            "border-bottom" := "2px solid gray",
            "text-align" := "right",
            "margin" := "2em 8px 1em 0px"),
          "p" #> (
            "margin" := "0px",
            "text-indent" := "1em",
            "text-align" := "justify")
        )
      )

    Serializer.serialize(ePub, "simple.epub")
  }
}
