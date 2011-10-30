package epub.css

import epub.css.Css._
import org.specs2.mutable._

class CssSpec extends Specification {
  "Css module" should {

    "successfully build empty ruleset" in {
      "p" #> () must beEqualTo(RuleSet(Selector("p"), Nil))
    }

    "successfully build ruleset with single rule" in {
      "p" #> (
        "font-size" := 12
        ) must beEqualTo(RuleSet(Selector("p"), Rule("font-size", "12") :: Nil))
    }

    "successfully build ruleset with several rules" in {
      ".test" #>(
        "color" := "red",
        "font-size" := 10,
        "border" := "0px"
        ) must beEqualTo(
        RuleSet(
          Selector(".test"),
          List(Rule("color", "red"), Rule("font-size", "10"), Rule("border", "0px"))))
    }
  }
}