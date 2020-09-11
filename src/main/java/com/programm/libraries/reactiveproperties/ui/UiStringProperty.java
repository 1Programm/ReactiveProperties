package com.programm.libraries.reactiveproperties.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.programm.libraries.reactiveproperties.core.StringProperty;

public class UiStringProperty extends StringProperty implements DocumentListener, PropertyChangeListener
{

  private final JComponent component;

  public UiStringProperty(JLabel label)
  {
    this.component = label;
    label.addPropertyChangeListener("text", this);
  }

  public UiStringProperty(JTextComponent textComponent)
  {
    this.component = textComponent;
    textComponent.getDocument().addDocumentListener(this);
  }

  public UiStringProperty(AbstractButton button)
  {
    this.component = button;
    button.addPropertyChangeListener("text", this);
  }

  @Override
  public void insertUpdate(DocumentEvent e)
  {
    onChange();
  }

  @Override
  public void removeUpdate(DocumentEvent e)
  {
    onChange();
  }

  @Override
  public void changedUpdate(DocumentEvent e)
  {
    onChange();
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt)
  {
    onChange();
  }

  private void onChange()
  {
    SwingUtilities.invokeLater(() -> {
      set(get());
      notifyChange();
    });
  }

  @Override
  public String get()
  {
    if (component instanceof JLabel)
    {
      return ((JLabel) component).getText();
    }
    else if (component instanceof JTextField)
    {
      return ((JTextField) component).getText();
    }
    else if (component instanceof JTextPane)
    {
      return ((JTextPane) component).getText();
    }
    else if (component instanceof JTextArea)
    {
      return ((JTextArea) component).getText();
    }
    else if (component instanceof AbstractButton)
    {
      return ((AbstractButton) component).getText();
    }

    throw new IllegalStateException("Invalid component type!");
  }

  @Override
  protected void setDirectly(String value)
  {
    if (component instanceof JLabel)
    {
      ((JLabel) component).setText(value);
    }
    else if (component instanceof JTextField)
    {
      ((JTextField) component).setText(value);
    }
    else if (component instanceof JTextPane)
    {
      ((JTextPane) component).setText(value);
    }
    else if (component instanceof JTextArea)
    {
      ((JTextArea) component).setText(value);
    }
    else if (component instanceof AbstractButton)
    {
      ((AbstractButton) component).setText(value);
    }
    else
    {
      throw new IllegalStateException("Invalid component type!");
    }
  }

}
