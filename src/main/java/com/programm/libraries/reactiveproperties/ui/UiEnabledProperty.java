package com.programm.libraries.reactiveproperties.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

import com.programm.libraries.reactiveproperties.core.BoolProperty;

public class UiEnabledProperty extends BoolProperty implements PropertyChangeListener
{

  private final JComponent component;

  public UiEnabledProperty(JComponent component)
  {
    this.component = component;
    this.component.addPropertyChangeListener("enabled", this);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt)
  {
    notifyChange();
  }

  @Override
  public Boolean get()
  {
    return component.isEnabled();
  }

  @Override
  protected void setDirectly(Boolean value)
  {
    component.setEnabled(value);
  }

}
