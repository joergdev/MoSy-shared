package com.github.joergdev.mosy.shared;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Supplier;

public class Utils
{
  public static boolean isPositive(Number number)
  {
    return number != null && number.intValue() >= 0;
  }

  public static boolean isEmpty(String str)
  {
    return str == null || str.trim().isEmpty();
  }

  public static <T> T getFirstElementOfCollection(Collection<T> col)
  {
    return isCollectionEmpty(col)
        ? null
        : col.iterator().next();
  }

  public static boolean isCollectionEmpty(Collection<?> col)
  {
    return col == null || col.isEmpty();
  }

  public static <T> T nvl(T t1, T t2)
  {
    return t1 == null
        ? t2
        : t1;
  }

  public static <T> T nvl(T t1, Supplier<T> t2)
  {
    return t1 == null
        ? t2.get()
        : t1;
  }

  public static <T> Collection<T> nvlCollection(Collection<T> col)
  {
    return col != null
        ? col
        : new ArrayList<>();
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
    return str == null
        ? null
        : Integer.valueOf(str);
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
}