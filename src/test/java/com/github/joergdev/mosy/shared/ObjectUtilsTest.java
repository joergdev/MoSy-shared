package com.github.joergdev.mosy.shared;

import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ObjectUtilsTest
{
  @Test
  public void testCopyValues()
  {
    BaseData apiBaseData = new BaseData();
    GlobalConfig dbGlobalConfig = new GlobalConfig();

    dbGlobalConfig.setMockActiveOnStartup(true);
    dbGlobalConfig.setMockActive(false);
    dbGlobalConfig.setTtlMockSession(1000);

    // Testcase 1
    ObjectUtils.copyValues(dbGlobalConfig, apiBaseData);

    assertEquals(true, apiBaseData.getMockActiveOnStartup());
    assertEquals(false, apiBaseData.getMockActive());
    assertEquals((Integer) 1000, apiBaseData.getTtlMockSession());

    // Testcase 2 (Exclude)
    apiBaseData = new BaseData();

    ObjectUtils.copyValues(dbGlobalConfig, apiBaseData, "mockActiveOnStartup", "ttlMockSession");

    assertEquals(null, apiBaseData.getMockActiveOnStartup());
    assertEquals(false, apiBaseData.getMockActive());
    assertEquals(null, apiBaseData.getTtlMockSession());
  }

  public class BaseData
  {
    // Data GlobalConfig
    private Boolean mockActiveOnStartup;
    private Boolean mockActive;
    private Boolean routingOnNoMockData;
    private Integer ttlMockSession;

    private Boolean record;

    private final List<Integer> interfaces = new ArrayList<>();

    private Integer countMockSessions;
    private Integer countRecords;

    public Boolean getMockActiveOnStartup()
    {
      return mockActiveOnStartup;
    }

    public void setMockActiveOnStartup(Boolean mockActiveOnStartup)
    {
      this.mockActiveOnStartup = mockActiveOnStartup;
    }

    public Boolean getMockActive()
    {
      return mockActive;
    }

    public void setMockActive(Boolean mockActive)
    {
      this.mockActive = mockActive;
    }

    public Integer getTtlMockSession()
    {
      return ttlMockSession;
    }

    public void setTtlMockSession(Integer ttlMockSession)
    {
      this.ttlMockSession = ttlMockSession;
    }

    public List<Integer> getInterfaces()
    {
      return interfaces;
    }

    public Integer getCountMockSessions()
    {
      return countMockSessions;
    }

    public void setCountMockSessions(Integer countMockSessions)
    {
      this.countMockSessions = countMockSessions;
    }

    public Boolean getRecord()
    {
      return record;
    }

    public void setRecord(Boolean record)
    {
      this.record = record;
    }

    public Boolean getRoutingOnNoMockData()
    {
      return routingOnNoMockData;
    }

    public void setRoutingOnNoMockData(Boolean routingOnNoMockData)
    {
      this.routingOnNoMockData = routingOnNoMockData;
    }

    public Integer getCountRecords()
    {
      return countRecords;
    }

    public void setCountRecords(Integer countRecords)
    {
      this.countRecords = countRecords;
    }
  }

  public class GlobalConfig
  {
    private LocalDateTime created;

    private Boolean mockActiveOnStartup;

    private Boolean mockActive;

    private Boolean routingOnNoMockData;

    private Integer ttlMockSession;

    public Boolean getMockActiveOnStartup()
    {
      return mockActiveOnStartup;
    }

    public void setMockActiveOnStartup(Boolean mockActiveOnStartup)
    {
      this.mockActiveOnStartup = mockActiveOnStartup;
    }

    public Boolean getMockActive()
    {
      return mockActive;
    }

    public void setMockActive(Boolean mockActive)
    {
      this.mockActive = mockActive;
    }

    public Integer getTtlMockSession()
    {
      return ttlMockSession;
    }

    public void setTtlMockSession(Integer ttlMockSession)
    {
      this.ttlMockSession = ttlMockSession;
    }

    public LocalDateTime getCreated()
    {
      return created;
    }

    public void setCreated(LocalDateTime created)
    {
      this.created = created;
    }

    public Boolean getRoutingOnNoMockData()
    {
      return routingOnNoMockData;
    }

    public void setRoutingOnNoMockData(Boolean routingOnNoMockData)
    {
      this.routingOnNoMockData = routingOnNoMockData;
    }
  }
}