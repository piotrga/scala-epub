package epub.dcmi

object Metadata {
  def apply(items: MetadataEntry*) = {
    val itemsMap = items.map( it => (it.getClass.getSimpleName, it) ).toMap
    new Metadata(itemsMap)
  }

  def id(id: String) = Identifier(id)
  def title(title: String) = Title(title)
  def creator(creator: String) = Creator(creator)
  def language(lang: String) = Language(lang)
  def date(date: java.util.Date) = Date(date)
  def publisher(publisher: String) = Publisher(publisher)
  def rights(license: String) = Rights(license)

  class Metadata (val items: Map[String,  MetadataEntry]) extends PartialFunction[String, MetadataEntry] {
    def toXML = {
      <metadata xmlns:dc="http://purl.org/dc/elements/1.1/"
                xmlns:dcterms="http://purl.org/dc/terms/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:opf="http://www.idpf.org/2007/opf">
        {for ((name, item) <- items) yield item.toXML}
      </metadata>
    }

    def apply(key: String) = items(key)
    def isDefinedAt(x: String) = items.isDefinedAt(x)
  }

  abstract class MetadataEntry { def toXML: xml.Node }

  case class Identifier(id: String) extends MetadataEntry {
    override def toXML = <dc:identifier id="bookid">{id}</dc:identifier>
  }

  case class Title(title: String) extends MetadataEntry {
    override def toXML = <dc:title>{title}</dc:title>
  }

  case class Creator(creator: String) extends MetadataEntry {
    override def toXML = <dc:creator>{creator}</dc:creator>
  }

  case class Language(lang: String) extends MetadataEntry {
    override def toXML = <dc:language>{lang}</dc:language>
  }

  case class Date(date: java.util.Date) extends MetadataEntry {
    override def toXML =
      <dc:date xsi:type="dcterms:W3CDTF">
        {java.text.DateFormat.getInstance().format(date)}
      </dc:date>
  }

  case class Publisher(publisher: String) extends MetadataEntry {
    override def toXML = <dc:publisher>{publisher}</dc:publisher>
  }
  case class Rights(license: String) extends MetadataEntry {
    override def toXML = <dc:rights>{license}</dc:rights>
  }

}
