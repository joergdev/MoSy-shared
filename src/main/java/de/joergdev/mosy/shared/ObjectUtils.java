package de.joergdev.mosy.shared;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.TypeMappingBuilder;

public class ObjectUtils
{
  public static void copyValues(Object source, Object destination, String... exclusions)
  {
    if (source != null && destination != null)
    {
      Mapper mapper;

      if (exclusions != null && exclusions.length > 0)
      {
        mapper = DozerBeanMapperBuilder.create().withMappingBuilder(new BeanMappingBuilder()
        {
          @Override
          protected void configure()
          {
            TypeMappingBuilder mappingBuilder = mapping(source.getClass(), destination.getClass());

            for (String exclusion : exclusions)
            {
              mappingBuilder.exclude(exclusion);
            }
          }
        }).build();
      }
      else
      {
        mapper = DozerBeanMapperBuilder.buildDefault();
      }

      mapper.map(source, destination);
    }
  }
}
