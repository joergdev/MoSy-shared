package com.github.joergdev.mosy.shared;

import java.io.Closeable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
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
      // do nothing
    }
  }

  public static Integer bigDecimal2Integer(BigDecimal bd)
  {
    return bd == null
        ? null
        : bd.intValue();
  }

  public static Integer bigInteger2Integer(BigInteger bi)
  {
    return bi == null
        ? null
        : bi.intValue();
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
      return localDateTimeToString(ld.atStartOfDay(), "dd.MM.yyyy");
    }

    return null;
  }

  public static String localDateTimeToString(LocalDateTime ld)
  {
    if (ld != null)
    {
      return localDateTimeToString(ld, "dd.MM.yyyy HH:mm:ss");
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
      ldt = cnvString2LocalDateTime(formatDate(date, "dd.MM.yyyy HH:mm:ss"));
    }

    return ldt;
  }

  /**
   * Format: dd.MM.yyyy HH:mm:ss
   *
   * @param value
   * @return  LocalDateTime
   */
  public static LocalDateTime cnvString2LocalDateTime(String value)
  {
    return cnvString2LocalDateTime(value, "dd.MM.yyyy HH:mm:ss");
  }

  /**
   * Example.: yyyy-MM-dd HH:mm
   *
   * @param value
   * @param pattern
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
      return parseDate(formatLocalDateTime(ldt), "dd.MM.yyyy HH:mm:ss", true);
    }
    else
    {
      return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
  }

  /**
   * Formatiert LocalDateTime to dd.MM.yyyy HH:mm:ss - string
   * 
   * @param localDateTime
   * @return String
   */
  public static String formatLocalDateTime(LocalDateTime localDateTime)
  {
    if (localDateTime == null)
    {
      return null;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

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
   * @param s String to parse
   * @param format
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
        throw new IllegalArgumentException(
            "'" + s + "' cannot be parsed to Date with format '" + formatString + "'");
      }
      else
      {
        return null;
      }
    }
  }
}