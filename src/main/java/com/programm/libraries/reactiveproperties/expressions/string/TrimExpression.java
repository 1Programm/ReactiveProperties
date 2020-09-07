package com.programm.libraries.reactiveproperties.expressions.string;

import com.programm.libraries.reactiveproperties.ObservableValue;

public class TrimExpression extends StringExpression {

    private final ObservableValue<String> value;

    public TrimExpression(ObservableValue<String> value) {
        super(value);
        this.value = value;
    }

    @Override
    public String get() {
        return value.get().trim();
    }
}
