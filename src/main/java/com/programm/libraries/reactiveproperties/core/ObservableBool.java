package com.programm.libraries.reactiveproperties.core;

import com.programm.libraries.reactiveproperties.ObservableValue;
import com.programm.libraries.reactiveproperties.expressions.bool.AndExpression;
import com.programm.libraries.reactiveproperties.expressions.bool.NotExpression;
import com.programm.libraries.reactiveproperties.expressions.bool.OrExpression;

public interface ObservableBool extends ObservableValue<Boolean> {
    ObservableBool TRUE = new ConstBool(true);
    ObservableBool FALSE = new ConstBool(false);

    default ObservableBool not(){
        return new NotExpression(this);
    }

    default ObservableBool and(ObservableValue<Boolean> other){
        return new AndExpression(this, other);
    }

    default ObservableBool or(ObservableValue<Boolean> other){
        return new OrExpression(this, other);
    }

}
