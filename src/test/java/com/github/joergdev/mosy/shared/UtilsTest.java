package com.github.joergdev.mosy.shared;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

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
  public void testFormatXml()
    throws Exception
  {
    String xmlUnformatted = "<Map><SingleVal>1</SingleVal><listMap  ><SingleValMapInList>10</SingleValMapInList></listMap><list>5</list></Map>";

    String xmlFormatted = Utils.formatXml(xmlUnformatted);

    System.out.println(xmlFormatted);

    StringBuilder bui = new StringBuilder();
    bui.append("<Map>").append("\r\n");
    bui.append("  <SingleVal>1</SingleVal>").append("\r\n");
    bui.append("  <listMap>").append("\r\n");
    bui.append("    <SingleValMapInList>10</SingleValMapInList>").append("\r\n");
    bui.append("  </listMap>").append("\r\n");
    bui.append("  <list>5</list>").append("\r\n");
    bui.append("</Map>").append("\r\n");

    assertEquals(bui.toString(), xmlFormatted);

    // second testcase
    xmlUnformatted = "<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:testMethod xmlns:ns2=\"http://services.test.mosy.joergdev.github.com/\"><action>2</action></ns2:testMethod></S:Body></S:Envelope>";

    xmlFormatted = Utils.formatXml(xmlUnformatted);

    System.out.println(xmlFormatted);

    bui = new StringBuilder();
    bui.append("<?xml version=\"1.0\" ?>").append("\r\n");
    bui.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">").append("\r\n");
    bui.append("  <S:Body>").append("\r\n");
    bui.append("    <ns2:testMethod xmlns:ns2=\"http://services.test.mosy.joergdev.github.com/\">")
        .append("\r\n");
    bui.append("      <action>2</action>").append("\r\n");
    bui.append("    </ns2:testMethod>").append("\r\n");
    bui.append("  </S:Body>").append("\r\n");
    bui.append("</S:Envelope>").append("\r\n");

    assertEquals(bui.toString(), xmlFormatted);
  }
}