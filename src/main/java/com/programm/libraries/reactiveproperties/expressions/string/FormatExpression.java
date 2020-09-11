package com.programm.libraries.reactiveproperties.expressions.string;

import java.util.Arrays;
import java.util.List;

import com.programm.libraries.reactiveproperties.ObservableValue;

public final class FormatExpression extends StringExpression
{
  private final List<ObservableValue<?>> myValues;
  private final String                   myFormatString;

  public FormatExpression(String formatString, ObservableValue<?>... values)
  {
    super(values);
    myFormatString = formatString;
    myValues = Arrays.asList(values);
  }

  @Override
  public String get()
  {
    Object[] values = new Object[myValues.size()];
    int i = 0;
    for (ObservableValue<?> observableValue : myValues)
    {
      values[i++] = observableValue.get();
    }
    return String.format(myFormatString, values);
  }
}
