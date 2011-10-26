package epub.dcmi

abstract class Metadata

object Metadata {
  def apply(items: Metadata*) = items.map( it => (it.getClass.getSimpleName, it) ).toMap

  def id(id: String) = Identifier(id)
  def title(title: String) = Title(title)
  def creator(creator: String) = Creator(creator)
  def language(lang: String) = Language(lang)
  def date(date: java.util.Date) = Date(date)
  def publisher(publisher: String) = Publisher(publisher)
  def rights(license: String) = Rights(license)

  case class Identifier(id: String) extends Metadata {
    def toXML = <dc:identifier id="bookid">{id}</dc:identifier>
  }

  case class Title(title: String) extends Metadata {
    def toXML = <dc:title>{title}</dc:title>
  }

  case class Creator(creator: String) extends Metadata {
    def toXML = <dc:creator>{creator}</dc:creator>
  }

  case class Language(lang: String) extends Metadata {
    def toXML = <dc:language>{lang}</dc:language>
  }

  case class Date(date: java.util.Date) extends Metadata {
    def toXML = <dc:date>{java.text.DateFormat.getInstance().format(date)}</dc:date>
  }

  case class Publisher(publisher: String) extends Metadata {
    def toXML = <dc:publisher>{publisher}</dc:publisher>
  }
  case class Rights(license: String) extends Metadata {
    def toXML = <dc:rights>{license}</dc:rights>
  }

}
