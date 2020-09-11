package com.programm.libraries.reactiveproperties.adapters;

import com.programm.libraries.reactiveproperties.AbstractProperty;
import com.programm.libraries.reactiveproperties.ChangeListener;
import com.programm.libraries.reactiveproperties.core.BoolProperty;
import com.programm.libraries.reactiveproperties.core.BoolValueProperty;
import com.programm.libraries.reactiveproperties.core.ObservableBool;

public abstract class AdapterProperty<S, D> extends AbstractProperty<D> implements ChangeListener
{
  private final AbstractProperty<S> wrappedPropery;
  private final BoolProperty        inSync           = new BoolValueProperty();
  private D                         lastValue;

  private boolean                   needsInitialSync = true;

  public AdapterProperty(AbstractProperty<S> wrappedPropery, D initialValue)
  {
    this.wrappedPropery = wrappedPropery;
    this.wrappedPropery.addWeakListener(this);
    this.lastValue = initialValue;
  }

  @Override
  protected void setDirectly(D value)
  {
    wrappedPropery.set(convertDestToSrc(value));
  }

  @Override
  public D get()
  {
    doInitialSync();
    return lastValue;
  }

  public ObservableBool inSync()
  {
    doInitialSync();
    return inSync;
  }

  @Override
  public void onChange()
  {
    trySync();
    notifyChange();
  }

  protected abstract D convertSrcToDest(S value);

  protected abstract S convertDestToSrc(D value);

  private void doInitialSync()
  {
    if (needsInitialSync)
    {
      trySync();
    }
  }

  private void trySync()
  {
    D result = convertSrcToDest(wrappedPropery.get());
    inSync.set(result != null);
    if (result != null)
    {
      lastValue = result;
    }
    needsInitialSync = false;
  }
}
