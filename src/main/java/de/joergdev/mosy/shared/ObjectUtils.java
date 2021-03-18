package de.joergdev.mosy.shared;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingBuilder;

public class ObjectUtils
{
  public static void copyValues(Object source, Object destination, String... exclusions)
  {
    if (source != null && destination != null)
    {
      DozerBeanMapper mapper = new DozerBeanMapper();

      if (exclusions != null && exclusions.length > 0)
      {
        mapper.addMapping(new BeanMappingBuilder()
        {
          protected void configure()
          {
            TypeMappingBuilder mappingBuilder = mapping(source.getClass(), destination.getClass());

            for (String exclusion : exclusions)
            {
              mappingBuilder.exclude(exclusion);
            }
          }
        });
      }

      mapper.map(source, destination);
    }
  }
}