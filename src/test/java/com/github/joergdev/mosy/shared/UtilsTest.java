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
}