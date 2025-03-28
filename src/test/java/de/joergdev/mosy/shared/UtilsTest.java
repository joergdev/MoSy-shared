package de.joergdev.mosy.shared;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;

public class UtilsTest
{
  @Test
  public void testMap2xml()
  {
    Map<String, Object> mapTest = new HashMap<>();
    mapTest.put("SingleVal", 1);

    Map<String, Object> innerMapTest = new HashMap<>();
    innerMapTest.put("SingleValInner", 2);
    mapTest.put("InnerMap", innerMapTest);

    List<Integer> list = Arrays.asList(3, 4, 5);
    mapTest.put("list", list);

    List<Map<String, Object>> listMap = new ArrayList<>();
    mapTest.put("listMap", listMap);

    Map<String, Object> mapInList1 = new HashMap<>();
    mapInList1.put("SingleValMapInList", 10);
    listMap.add(mapInList1);

    Map<String, Object> mapInList2 = new HashMap<>();
    mapInList2.put("SingleValMapInList", 11);
    listMap.add(mapInList2);

    String xml = Utils.map2xml(mapTest, true);

    System.out.println(xml);

    StringBuilder bui = new StringBuilder();
    bui.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    bui.append("<Map>");
    bui.append("  <SingleVal type=\"java.lang.Integer\">1</SingleVal>");
    bui.append("  <listMap>");
    bui.append("    <SingleValMapInList type=\"java.lang.Integer\">10</SingleValMapInList>");
    bui.append("  </listMap>");
    bui.append("  <listMap>");
    bui.append("    <SingleValMapInList type=\"java.lang.Integer\">11</SingleValMapInList>");
    bui.append("  </listMap>");
    bui.append("  <InnerMap>");
    bui.append("    <SingleValInner type=\"java.lang.Integer\">2</SingleValInner>");
    bui.append("  </InnerMap>");
    bui.append("  <list type=\"java.lang.Integer\">3</list>");
    bui.append("  <list type=\"java.lang.Integer\">4</list>");
    bui.append("  <list type=\"java.lang.Integer\">5</list>");
    bui.append("</Map>");

    assertEquals(bui.toString(), xml.replace("\r\n", ""));
  }

  @Test
  public void testFormatJSON()
  {
    String inputJSON = "{\"name\": \"John\", \"age\": 30}";

    String expectedFormattedJSON = "{\r\n  \"name\" : \"John\",\r\n  \"age\" : 30\r\n}";

    try
    {
      assertEquals(expectedFormattedJSON, Utils.formatJSON(inputJSON, true));
    }
    catch (IllegalArgumentException ex)
    {
      ex.printStackTrace();
      fail(ex.getMessage());
    }
  }

  @Test
  public void testObject2Json()
    throws JsonProcessingException
  {
    String json = Utils.object2Json(new Person("Fred", 80));
    assertEquals("{\"name\":\"Fred\",\"age\":80}", json);

    json = Utils.object2Json(new Person("Fred", null));
    assertEquals("{\"name\":\"Fred\"}", json);
  }

  @Test
  public void testJsonToObject()
  {
    Person p = Utils.jsonToObject("{\"name\":\"Fred\",\"age\":80}", Person.class);
    assertNotNull(p);
    assertEquals("Fred", p.getName());
    assertEquals(Integer.valueOf(80), p.getAge());
  }

  private static class Person
  {
    private String name;
    private Integer age;

    @SuppressWarnings("unused")
    public Person()
    {

    }

    public Person(String name, Integer age)
    {
      setName(name);
      setAge(age);
    }

    @SuppressWarnings("unused")
    public String getName()
    {
      return name;
    }

    public void setName(String name)
    {
      this.name = name;
    }

    @SuppressWarnings("unused")
    public Integer getAge()
    {
      return age;
    }

    public void setAge(Integer age)
    {
      this.age = age;
    }
  }

  @Test
  public void testFormatXml()
    throws Exception
  {
    String xmlUnformatted = "<Map><SingleVal>1</SingleVal><listMap  ><SingleValMapInList>10</SingleValMapInList></listMap><list>5</list></Map>";

    String xmlFormatted = Utils.formatXml(xmlUnformatted);

    System.out.println(xmlFormatted);

    StringBuilder bui = new StringBuilder();
    bui.append("<Map>").append("\n");
    bui.append("  <SingleVal>1</SingleVal>").append("\n");
    bui.append("  <listMap>").append("\n");
    bui.append("    <SingleValMapInList>10</SingleValMapInList>").append("\n");
    bui.append("  </listMap>").append("\n");
    bui.append("  <list>5</list>").append("\n");
    bui.append("</Map>");

    assertEquals(bui.toString(), xmlFormatted);

    // second testcase
    xmlUnformatted = "<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:testMethod xmlns:ns2=\"http://services.test.mosy.joergdev.github.com/\"><action>2</action></ns2:testMethod></S:Body></S:Envelope>";

    xmlFormatted = Utils.formatXml(xmlUnformatted);

    System.out.println(xmlFormatted);

    bui = new StringBuilder();
    bui.append("<?xml version=\"1.0\" ?>").append("\n");
    bui.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">").append("\n");
    bui.append("  <S:Body>").append("\n");
    bui.append("    <ns2:testMethod xmlns:ns2=\"http://services.test.mosy.joergdev.github.com/\">").append("\n");
    bui.append("      <action>2</action>").append("\n");
    bui.append("    </ns2:testMethod>").append("\n");
    bui.append("  </S:Body>").append("\n");
    bui.append("</S:Envelope>");

    assertEquals(bui.toString(), xmlFormatted);

    // third testcase
    xmlUnformatted = "<return>m_one_m</return>";

    xmlFormatted = Utils.formatXml(xmlUnformatted);

    System.out.println(xmlFormatted);

    assertEquals(xmlUnformatted, xmlFormatted);
  }
}
