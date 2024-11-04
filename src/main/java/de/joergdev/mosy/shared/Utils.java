package de.joergdev.mosy.shared;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils
{
  public static final String DATE_FORMAT_DD_MM_YYYY = "dd.MM.yyyy";
  public static final String DATE_FORMAT_DD_MM_YYYY_HH_MM_SS = "dd.MM.yyyy HH:mm:ss";
  public static final String DATE_FORMAT_DD_MM_YYYY_HH_MM_SS_UNDERSCORES = "dd_MM_yyyy_HH_mm_ss";

  public static boolean isPositive(Number number)
  {
    return number != null && number.intValue() >= 0;
  }

  public static boolean isNumeric(String str)
  {
    if (str != null && !str.trim().isEmpty())
    {
      try
      {
        Long.valueOf(str);

        return true;
      }
      catch (Exception exDontCare)
      {

      }
    }

    return false;
  }

  public static boolean isEmpty(String str)
  {
    return str == null || str.trim().isEmpty();
  }

  public static <T> T getFirstElementOfCollection(Collection<T> col)
  {
    return isCollectionEmpty(col) ? null : col.iterator().next();
  }

  public static boolean isCollectionEmpty(Collection<?> col)
  {
    return col == null || col.isEmpty();
  }

  public static String nvl(String s)
  {
    return s == null ? "" : s;
  }

  public static <T> T nvl(T t1, T t2)
  {
    return t1 == null ? t2 : t1;
  }

  public static <T> T nvl(T t1, Supplier<T> t2)
  {
    return t1 == null ? t2.get() : t1;
  }

  public static <T> Collection<T> nvlCollection(Collection<T> col)
  {
    return col != null ? col : new ArrayList<>();
  }

  public static <T, K> Map<T, K> nvlMap(Map<T, K> map)
  {
    return map != null ? map : new HashMap<>();
  }

  public static <T extends Comparable<T>> T min(@SuppressWarnings("unchecked") T... t)
  {
    if (t == null || t.length == 0)
    {
      return null;
    }

    return Arrays.asList(t).stream().min(getDefaultComparator()).orElse(null);
  }

  public static <T extends Comparable<T>> Comparator<T> getDefaultComparator()
  {
    return (o1, o2) -> o1.compareTo(o2);
  }

  public static Integer asInteger(String str)
  {
    return isEmpty(str) ? null : Integer.valueOf(str);
  }

  public static String asString(Integer i)
  {
    return i == null ? null : String.valueOf(i);
  }

  public static void safeClose(Closeable... closeables)
  {
    if (closeables != null)
    {
      for (Closeable closeable : closeables)
      {
        if (closeable != null)
        {
          try
          {
            closeable.close();
          }
          catch (Exception eDontCare)
          {
            // ignore
          }
        }
      }
    }
  }

  public static boolean isEqual(Object o1, Object o2)
  {
    if (Objects.equals(o1, o2))
    {
      return true;
    }
    else if (o1 instanceof BigDecimal && o2 instanceof BigDecimal)
    {
      return ((BigDecimal) o1).compareTo((BigDecimal) o2) == 0;
    }

    return false;
  }

  public static void delay(long time)
  {
    try
    {
      Thread.sleep(time);
    }
    catch (InterruptedException e)
    {
      // Restore interrupted state...
      Thread.currentThread().interrupt();
    }
  }

  public static Integer bigDecimal2Integer(BigDecimal bd)
  {
    return bd == null ? null : bd.intValue();
  }

  public static Integer bigInteger2Integer(BigInteger bi)
  {
    return bi == null ? null : bi.intValue();
  }

  public static <T> boolean addToCollectionIfNotNull(Collection<T> col, T t)
  {
    if (col != null && t != null)
    {
      return col.add(t);
    }

    return false;
  }

  public static String localDateToString(LocalDate ld)
  {
    if (ld != null)
    {
      return localDateTimeToString(ld.atStartOfDay(), DATE_FORMAT_DD_MM_YYYY);
    }

    return null;
  }

  public static String localDateTimeToString(LocalDateTime ld)
  {
    if (ld != null)
    {
      return localDateTimeToString(ld, DATE_FORMAT_DD_MM_YYYY_HH_MM_SS);
    }

    return null;
  }

  public static String localDateTimeToString(LocalDateTime ld, String pattern)
  {
    if (ld != null)
    {
      return DateTimeFormatter.ofPattern(pattern).format(ld);
    }

    return null;
  }

  // --

  public static LocalDateTime dateToLocalDateTime(Date date)
  {
    LocalDateTime ldt = null;

    if (date != null)
    {
      // if sql-Date no toInstant possible
      if (date instanceof java.sql.Date)
      {
        java.sql.Date sqlDate = (java.sql.Date) date;
        ldt = LocalDateTime.of(sqlDate.toLocalDate(), LocalTime.of(0, 0));
      }
      else
      {
        ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
      }
    }

    // Bug / difference in java intern calendarsystem Date/LocalDate(Time) before xxxx.
    // so for years < 1900 go along with String format/parse.
    if (ldt != null && ldt.getYear() < 1900)
    {
      ldt = cnvString2LocalDateTime(formatDate(date, DATE_FORMAT_DD_MM_YYYY_HH_MM_SS));
    }

    return ldt;
  }

  /**
   * @param value - date in format dd.MM.yyyy HH:mm:ss
   * @return  LocalDateTime
   */
  public static LocalDateTime cnvString2LocalDateTime(String value)
  {
    return cnvString2LocalDateTime(value, DATE_FORMAT_DD_MM_YYYY_HH_MM_SS);
  }

  /**
   * @param value - date as string
   * @param pattern - pattern for date
   * @return LocalDateTime
   */
  public static LocalDateTime cnvString2LocalDateTime(String value, String pattern)
  {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

    return LocalDateTime.parse(value, formatter);
  }

  public static Date localDateTimeToDate(LocalDateTime ldt)
  {
    if (ldt == null)
    {
      return null;
    }

    // Bug / difference in java intern calendarsystem Date/LocalDate(Time) before xxxx.
    // so for years < 1900 go along with String format/parse.
    if (ldt.getYear() < 1900)
    {
      return parseDate(formatLocalDateTime(ldt), DATE_FORMAT_DD_MM_YYYY_HH_MM_SS, true);
    }
    else
    {
      return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
  }

  /**
   * Formatiert LocalDateTime to dd.MM.yyyy HH:mm:ss - string
   * 
   * @param localDateTime - dateTime
   * @return String
   */
  public static String formatLocalDateTime(LocalDateTime localDateTime)
  {
    if (localDateTime == null)
    {
      return null;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_DD_MM_YYYY_HH_MM_SS);

    return localDateTime.format(formatter);
  }

  private static String formatDate(Date date, String format)
  {
    if (date == null)
    {
      return null;
    }

    DateFormat df = new SimpleDateFormat(format);

    return df.format(date);
  }

  /**
   * Transforms a String to Date.
   *
   * @param s - String to parse
   * @param formatString - pattern
   * @param throwException - throw exception on failure if true, else return null
   * @return Date parsed Date or <code>null</code>, if s <code>null</code>
   * @throws IllegalArgumentException if s has invalid format or content
   */
  public static Date parseDate(String s, String formatString, boolean throwException)
  {
    if (isEmpty(s))
    {
      return null;
    }

    DateFormat df = new SimpleDateFormat(formatString);

    /**
     * Check for valid input. 32.01.2003 => Error.
     * If not setLenient = false then 32.01.2003 would get 01.02.2003.
     */
    df.setLenient(false);

    try
    {
      Date result = df.parse(s);
      return result;
    }
    catch (ParseException ex)
    {
      if (throwException)
      {
        throw new IllegalArgumentException("'" + s + "' cannot be parsed to Date with format '" + formatString + "'");
      }
      else
      {
        return null;
      }
    }
  }

  public static Map<String, Object> xmlToMap(String xml)
  {
    try
    {
      Document doc = getDocumentFromInputString(xml);

      Map<String, Object> map = new HashMap<>();

      nodeToMap(doc.getDocumentElement(), map);

      return map;
    }
    catch (Exception ex)
    {
      throw new IllegalStateException(ex);
    }
  }

  private static void nodeToMap(Node node, Map<String, Object> map)
  {
    String name = node.getNodeName();
    String content = node.getTextContent();

    List<Node> childNodes = getChildNodes(node);

    // dont transfer empty nodes
    if (!childNodes.isEmpty() || !Utils.isEmpty(content))
    {
      if (childNodes.isEmpty())
      {
        map.put(name, content);
      }
      else
      {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> listForTag = (List<Map<String, Object>>) map.get(name);
        if (listForTag == null)
        {
          listForTag = new ArrayList<>();
          map.put(name, listForTag);
        }

        Map<String, Object> subMap = new HashMap<>();
        listForTag.add(subMap);

        childNodes.forEach(c -> nodeToMap(c, subMap));
      }
    }
  }

  private static List<Node> getChildNodes(Node rootNode)
  {
    List<Node> nodes = new ArrayList<>();

    org.w3c.dom.NodeList nl = rootNode.getChildNodes();

    for (int x = 0; x < nl.getLength(); x++)
    {
      Node n = nl.item(x);

      if (isRelevantNode(n))
      {
        nodes.add(n);
      }
    }

    return nodes;
  }

  private static boolean isRelevantNode(Node n)
  {
    if (Node.ELEMENT_NODE == n.getNodeType())
    {
      return true;
    }
    // TEXT_NODE -> false

    return false;
  }

  public static Document getDocumentFromInputString(String xml)
    throws Exception
  {
    Charset charset = getCharsetFromXml(xml);

    // Default = UTF-8
    if (charset == null)
    {
      charset = StandardCharsets.UTF_8;
    }

    byte[] bytes = xml.getBytes(charset);

    return getDocumentFromInputStream(() -> new ByteArrayInputStream(bytes));
  }

  private static Charset getCharsetFromXml(String xml)
  {
    try
    {
      XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(new StringReader(xml));

      String charsetStr = xmlStreamReader.getCharacterEncodingScheme();

      return Utils.isEmpty(charsetStr) ? null : Charset.forName(charsetStr);
    }
    catch (Exception ex)
    {
      throw new IllegalStateException(ex);
    }
  }

  private static Document getDocumentFromInputStream(Supplier<InputStream> isSupplier)
    throws Exception
  {
    try (InputStream is2 = isSupplier.get())
    {
      DocumentBuilderFactory dbFactory = getDocumentBuilderFactory();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(is2);

      // opt. but recommended
      doc.getDocumentElement().normalize();

      return doc;
    }
  }

  public static String map2xml(Map<?, ?> map, boolean addType)
  {
    org.jdom2.Document document = new org.jdom2.Document();
    Element root = new Element("Map");

    map2xml(map, root, addType);

    document.setContent(root);

    return new XMLOutputter(Format.getPrettyFormat()).outputString(document);
  }

  private static void map2xml(Map<?, ?> map, Element parent, boolean addType)
  {
    for (Entry<?, ?> entry : map.entrySet())
    {
      Object key = entry.getKey();
      Object value = entry.getValue();

      if (value != null)
      {
        if (value instanceof Collection)
        {
          Collection<?> col = (Collection<?>) value;
          for (Object colValue : col)
          {
            addChildNode(parent, key, colValue, addType);
          }
        }
        else
        {
          addChildNode(parent, key, value, addType);
        }
      }
    }
  }

  private static void addChildNode(Element parent, Object key, Object value, boolean addType)
  {
    if (value != null)
    {
      Element child = new Element(key.toString());

      if (value instanceof Map)
      {
        map2xml((Map<?, ?>) value, child, addType);

        if (!child.getChildren().isEmpty())
        {
          parent.addContent(child);
        }
      }
      else
      {
        String textValue = value.toString();

        // special format for date for readability
        if (value instanceof java.util.Date)
        {
          DateFormat df = new SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY_HH_MM_SS);
          textValue = df.format(value);
        }
        else if (value instanceof LocalDate)
        {
          LocalDate lD = (LocalDate) value;
          textValue = lD.format(DateTimeFormatter.ofPattern(DATE_FORMAT_DD_MM_YYYY));
        }
        else if (value instanceof LocalDateTime)
        {
          LocalDateTime lDT = (LocalDateTime) value;
          textValue = lDT.format(DateTimeFormatter.ofPattern(DATE_FORMAT_DD_MM_YYYY_HH_MM_SS));
        }
        else if (value instanceof LocalTime)
        {
          LocalTime lT = (LocalTime) value;
          textValue = lT.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }

        if (addType)
        {
          child.setAttribute("type", value.getClass().getName());
        }

        child.setText(textValue);
        parent.addContent(child);
      }
    }
  }

  public static String formatXml(final String xml)
  {
    if (isEmpty(xml))
    {
      return null;
    }

    try (StringWriter writer = new StringWriter())
    {
      DocumentBuilderFactory factory = getDocumentBuilderFactory();
      factory.setIgnoringElementContentWhitespace(true);
      DocumentBuilder builder = factory.newDocumentBuilder();

      Document doc = builder.parse(new InputSource(new StringReader(xml.trim())));

      Transformer transformer = getTransformerFactory().newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

      transformer.transform(new DOMSource(doc), new StreamResult(writer));

      String xmlFormatted = writer.toString();

      if (xml.startsWith("<?xml"))
      {
        xmlFormatted = xml.substring(0, xml.indexOf("?>") + 2) + "\n" + xmlFormatted;
      }

      if (xmlFormatted.endsWith("\n") && !xml.endsWith("\n"))
      {
        xmlFormatted = xmlFormatted.substring(0, xmlFormatted.length() - 2);
      }

      xmlFormatted = xmlFormatted.replace("\r", "");

      return xmlFormatted;
    }
    catch (Exception ex)
    {
      throw new IllegalStateException(ex);
    }
  }

  private static TransformerFactory getTransformerFactory()
    throws TransformerFactoryConfigurationError
  {
    TransformerFactory trFactory = TransformerFactory.newInstance();

    // prohibit the use of all protocols by external entities
    trFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    trFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

    return trFactory;
  }

  private static DocumentBuilderFactory getDocumentBuilderFactory()
  {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    // prohibit the use of all protocols by external entities
    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

    return factory;
  }

  public static String formatJSON(final String json, boolean failOnParseError)
  {
    if (isEmpty(json))
    {
      return json;
    }

    try
    {
      ObjectMapper objectMapper = new ObjectMapper();
      Object jsonObject = objectMapper.readValue(json, Object.class);
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    }
    catch (JsonParseException ex)
    {
      if (failOnParseError)
      {
        throw new IllegalArgumentException(json, ex);
      }

      return json;
    }
    catch (IOException ex)
    {
      throw new IllegalArgumentException(json, ex);
    }
  }

  public static String object2Json(Object o)
    throws JsonProcessingException
  {
    return object2Json(o, true);
  }

  public static String object2Json(Object o, boolean withoutNullValues)
    throws JsonProcessingException
  {
    if (o == null)
    {
      return null;
    }

    ObjectMapper mapper = new ObjectMapper();

    if (withoutNullValues)
    {
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    return mapper.writeValueAsString(o);
  }

  public static String removeFromStringStart(String str, String strToRemove)
  {
    if (str != null && strToRemove != null && str.startsWith(strToRemove))
    {
      if (str.length() > strToRemove.length())
      {
        str = str.substring(strToRemove.length());
      }
      else
      {
        str = "";
      }
    }

    return str;
  }

  @SafeVarargs
  public static <K, V> Map<K, V> mapOfEntries(SimpleEntry<K, V>... values)
  {
    return Stream.of(values).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }

  public static <K, V> SimpleEntry<K, V> mapEntry(K key, V value)
  {
    return new SimpleEntry<>(key, value);
  }

  public static boolean mapContainsMap(Map<?, ?> mapBase, Map<?, ?> mapCheckIfContains)
  {
    mapBase = Utils.nvlMap(mapBase);
    mapCheckIfContains = Utils.nvlMap(mapCheckIfContains);

    for (Entry<?, ?> entryCheckIfContains : mapCheckIfContains.entrySet())
    {
      if (!mapBase.containsKey(entryCheckIfContains.getKey()) || !Objects.equals(mapBase.get(entryCheckIfContains.getKey()), entryCheckIfContains.getValue()))
      {
        return false;
      }
    }

    return true;
  }

  /**
   * Get the system-property if set, otherwise the enironment variable.
   * 
   * @param prop - system or enironment variable name
   * @return String - system or enironment variable value
   */
  public static String getSystemProperty(String prop)
  {
    String value = System.getProperty(prop);

    if (value == null)
    {
      value = System.getenv(prop);
    }

    return value;
  }
}
