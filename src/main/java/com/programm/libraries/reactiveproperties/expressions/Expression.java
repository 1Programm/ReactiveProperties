package com.programm.libraries.reactiveproperties.expressions;

import com.programm.libraries.reactiveproperties.AbstractObservableValue;
import com.programm.libraries.reactiveproperties.ChangeListener;
import com.programm.libraries.reactiveproperties.ObservableValue;

public abstract class Expression <T> extends AbstractObservableValue<T> implements ObservableValue<T> {

    private final ChangeListener myListener = this::notifyChange;

    protected Expression(ObservableValue<?>... values){
        if(values.length == 0){
            throw new IllegalArgumentException("Can't create an Expression without arguments!");
        }

        for(ObservableValue<?> val : values){
            val.addWeakListener(myListener);
        }
    }

}
