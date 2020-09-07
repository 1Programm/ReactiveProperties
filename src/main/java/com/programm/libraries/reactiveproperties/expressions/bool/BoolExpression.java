package com.programm.libraries.reactiveproperties.expressions.bool;

import com.programm.libraries.reactiveproperties.ObservableValue;
import com.programm.libraries.reactiveproperties.core.ObservableBool;
import com.programm.libraries.reactiveproperties.expressions.Expression;

public abstract class BoolExpression extends Expression<Boolean> implements ObservableBool {

    public BoolExpression(ObservableValue<?>... values) {
        super(values);
    }
}
