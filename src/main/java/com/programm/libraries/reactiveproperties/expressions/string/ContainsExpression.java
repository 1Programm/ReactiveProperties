package com.programm.libraries.reactiveproperties.expressions.string;

import com.programm.libraries.reactiveproperties.ObservableValue;
import com.programm.libraries.reactiveproperties.core.ConstString;
import com.programm.libraries.reactiveproperties.expressions.bool.BoolExpression;

public class ContainsExpression extends BoolExpression {

    private final ObservableValue<String> value;
    private final ObservableValue<String> toContain;

    public ContainsExpression(ObservableValue<String> value, String toContain) {
        this(value, new ConstString(toContain));
    }

    public ContainsExpression(ObservableValue<String> value, ObservableValue<String> toContain) {
        super(value);
        this.value = value;
        this.toContain = toContain;
    }

    @Override
    public Boolean get() {
        String sValue = value.get();
        String sContain = toContain.get();
        return sValue.contains(sContain);
    }
}
