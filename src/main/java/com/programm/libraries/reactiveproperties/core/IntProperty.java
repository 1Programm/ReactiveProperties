package com.programm.libraries.reactiveproperties.core;

import com.programm.libraries.reactiveproperties.AbstractProperty;
import com.programm.libraries.reactiveproperties.ObservableValue;
import com.programm.libraries.reactiveproperties.expressions.integer.IntComparisonExpression;

public abstract class IntProperty extends AbstractProperty<Integer> implements ObservableInt
{

  public void increment()
  {
    set(get() + 1);
  }

  public void decrement()
  {
    set(get() - 1);
  }

  @Override
  public ObservableBool equalTo(ObservableValue<Integer> value)
  {
    return IntComparisonExpression.EqualTo(this, value);
  }

  @Override
  public ObservableBool greaterThan(ObservableValue<Integer> value)
  {
    return IntComparisonExpression.GreaterThan(this, value);
  }

  @Override
  public ObservableBool lessThan(ObservableValue<Integer> value)
  {
    return IntComparisonExpression.LessThan(this, value);
  }

  @Override
  public ObservableBool greaterThanEqualTo(ObservableValue<Integer> value)
  {
    return IntComparisonExpression.GreaterThanEqual(this, value);
  }

  @Override
  public ObservableBool lessThanEqualTo(ObservableValue<Integer> value)
  {
    return IntComparisonExpression.LessThanEqual(this, value);
  }

  @Override
  public ObservableBool equalTo(int value)
  {
    return IntComparisonExpression.EqualTo(this, value);
  }

  @Override
  public ObservableBool greaterThan(int value)
  {
    return IntComparisonExpression.GreaterThan(this, value);
  }

  @Override
  public ObservableBool lessThan(int value)
  {
    return IntComparisonExpression.LessThan(this, value);
  }

  @Override
  public ObservableBool greaterThanEqualTo(int value)
  {
    return IntComparisonExpression.GreaterThanEqual(this, value);
  }

  @Override
  public ObservableBool lessThanEqualTo(int value)
  {
    return IntComparisonExpression.LessThanEqual(this, value);
  }

}
