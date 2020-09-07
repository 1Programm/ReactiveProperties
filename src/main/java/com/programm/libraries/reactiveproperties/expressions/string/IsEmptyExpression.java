package com.programm.libraries.reactiveproperties.expressions.string;


import com.programm.libraries.reactiveproperties.ObservableValue;
import com.programm.libraries.reactiveproperties.expressions.bool.BoolExpression;

public class IsEmptyExpression extends BoolExpression {

    private final ObservableValue<String> value;

    public IsEmptyExpression(ObservableValue<String> value) {
        super(value);
        this.value = value;
    }

    @Override
    public Boolean get() {
        return value.get().isEmpty();
    }
}
