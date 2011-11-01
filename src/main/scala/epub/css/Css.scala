package epub.css

object Css {

  implicit def strToCssSelector(str: String) = Selector(str)
  implicit def strToRule(str: String) = RuleBuilder(str)

  case class Rule (property: String,  value: String) {
    override def toString = "\t" + property + ": " + value + ";\n"
  }

  case class RuleSet (selector: Selector,
                      rules: List[Rule]) {
    override def toString = selector + " {\n" + rules.mkString + "}\n"
  }

  case class Selector (selector: String) {

    def #>(rules: Rule*) = RuleSet(this, rules.toList)

    override def toString = selector
  }

  class RuleBuilder private (val property: String) {
    def :=(value: Any) = Rule(property, value.toString)
  }

  object RuleBuilder {
    def apply(property: String) = new RuleBuilder(property)
  }
}
