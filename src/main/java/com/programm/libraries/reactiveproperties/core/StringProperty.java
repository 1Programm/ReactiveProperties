package com.programm.libraries.reactiveproperties.core;

import com.programm.libraries.reactiveproperties.AbstractProperty;
import com.programm.libraries.reactiveproperties.expressions.string.IsEmptyExpression;
import com.programm.libraries.reactiveproperties.expressions.string.SizeExpression;
import com.programm.libraries.reactiveproperties.expressions.string.TrimExpression;

public abstract class StringProperty extends AbstractProperty<String> implements ObservableString {

    public void clear(){
        set("");
    }

    @Override
    public ObservableBool isEmpty() {
        return new IsEmptyExpression(this);
    }

    @Override
    public ObservableString trim() {
        return new TrimExpression(this);
    }

    @Override
    public ObservableInt size() {
        return new SizeExpression(this);
    }
}
