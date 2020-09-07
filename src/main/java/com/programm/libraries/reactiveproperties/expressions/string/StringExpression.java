package com.programm.libraries.reactiveproperties.expressions.string;

import com.programm.libraries.reactiveproperties.ObservableValue;
import com.programm.libraries.reactiveproperties.core.ObservableBool;
import com.programm.libraries.reactiveproperties.core.ObservableInt;
import com.programm.libraries.reactiveproperties.core.ObservableString;
import com.programm.libraries.reactiveproperties.expressions.Expression;

public abstract class StringExpression extends Expression<String> implements ObservableString {

    protected StringExpression(ObservableValue<?>... values){
        super(values);
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
