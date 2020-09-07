package com.programm.libraries.reactiveproperties.expressions.string;

import com.programm.libraries.reactiveproperties.ObservableValue;
import com.programm.libraries.reactiveproperties.expressions.integer.IntExpression;

public class SizeExpression extends IntExpression {

    private final ObservableValue<String> value;

    public SizeExpression(ObservableValue<String> value) {
        super(value);
        this.value = value;
    }

    @Override
    public Integer get() {
        return value.get().length();
    }
}
