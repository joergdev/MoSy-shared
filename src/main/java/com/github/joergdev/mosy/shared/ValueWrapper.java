package com.github.joergdev.mosy.shared;

public class ValueWrapper<T>
{
  private T value;

  public ValueWrapper(T value)
  {
    this.value = value;
  }

  public T getValue()
  {
    return value;
  }

  public void setValue(T value)
  {
    this.value = value;
  }
}