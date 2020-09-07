package com.programm.libraries.reactiveproperties;

import com.programm.libraries.reactiveproperties.core.ObservableBool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class BindingsManager {

    private static final class DestUpdater <T> implements Runnable {
        private final SettableValue<T> dest;
        private final ObservableValue<T> src;

        public DestUpdater(SettableValue<T> dest, ObservableValue<T> src) {
            this.dest = dest;
            this.src = src;
        }

        @Override
        public void run() {
            dest.set(src.get());
        }

        @Override
        public boolean equals(Object o){
            if(this == o) return true;
            if(o == null || getClass() != o.getClass()) return false;
            DestUpdater<?> other = (DestUpdater<?>)o;
            return Objects.equals(dest, other.dest) && Objects.equals(src, other.src);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dest, src);
        }
    }

    private final class OneWayBinding <T> implements ChangeListener {
        private final SettableValue<T> dest;
        private final ObservableValue<T> src;
        private final ObservableValue<Boolean> enabled;

        public OneWayBinding(SettableValue<T> dest, ObservableValue<T> src, ObservableValue<Boolean> enabled){
            this.dest = dest;
            this.src = src;
            this.enabled = enabled;

            this.src.addListener(this);
            this.enabled.addListener(this);

            onChange();
        }

        @Override
        public void onChange() {
            if(enabled.get()){
                invoker.enqueue(new DestUpdater<>(dest, src));
            }
        }

        public void dispose(){
            src.removeListener(this);
            src.removeListener(this);
        }
    }

    private final class TwoWayBinding <T> {
        private final SettableValue<T> val1;
        private final SettableValue<T> val2;
        private final ChangeListener val1ChangedListener;
        private final ChangeListener val2ChangedListener;

        public TwoWayBinding(SettableValue<T> val1, SettableValue<T> val2) {
            this.val1 = val1;
            this.val2 = val2;
            this.val1ChangedListener = () -> invoker.enqueue(new DestUpdater<>(val2, val1));
            this.val2ChangedListener = () -> invoker.enqueue(new DestUpdater<>(val1, val2));

            this.val1.addListener(val1ChangedListener);
            this.val2.addListener(val2ChangedListener);

            val2ChangedListener.onChange();
        }

        public void dispose(){
            val1.removeListener(val1ChangedListener);
            val2.removeListener(val2ChangedListener);
        }
    }


    private final List<OneWayBinding<?>> oneWayBindings = new ArrayList<>();
    private final List<TwoWayBinding<?>> twoWayBindings = new ArrayList<>();
    private final BatchInvoker invoker;

    public BindingsManager() {
        this.invoker = new BatchInvoker();
    }

    public BindingsManager(BatchInvoker.Strategy strategy){
        this.invoker = new BatchInvoker(strategy);
    }

    public <T> void bind(SettableValue<T> dest, ObservableValue<T> src){
        bind(dest, src, ObservableBool.TRUE);
    }

    public <T> void bind(SettableValue<T> dest, ObservableValue<T> src, ObservableValue<Boolean> enabled){
        release(dest);

        oneWayBindings.add(new OneWayBinding<>(dest, src, enabled));
    }

    public <T> void bindTwoWay(SettableValue<T> val1, SettableValue<T> val2){
        releaseTwoWay(val1, val2);

        twoWayBindings.add(new TwoWayBinding<>(val1, val2));
    }

    public void release(SettableValue<?> dest){
        Iterator<OneWayBinding<?>> i = oneWayBindings.iterator();
        while (i.hasNext()){
            OneWayBinding<?> binding = i.next();
            if(binding.dest == dest){
                binding.dispose();
                i.remove();
                return;
            }
        }
    }

    public <T> void releaseTwoWay(SettableValue<T> val1, SettableValue<T> val2){
        Iterator<TwoWayBinding<?>> i = twoWayBindings.iterator();
        while (i.hasNext()){
            TwoWayBinding<?> binding = i.next();
            if(binding.val1 == val1 && binding.val2 == val2){
                binding.dispose();
                i.remove();
                return;
            }
        }
    }

    public <T> void releaseTwoWay(SettableValue<T> value){
        Iterator<TwoWayBinding<?>> i = twoWayBindings.iterator();
        while (i.hasNext()){
            TwoWayBinding<?> binding = i.next();
            if(binding.val1 == value || binding.val2 == value){
                binding.dispose();
                i.remove();
            }
        }
    }

    public void releaseAll(){
        for(OneWayBinding<?> binding : oneWayBindings){
            binding.dispose();
        }
        oneWayBindings.clear();


        for(TwoWayBinding<?> binding : twoWayBindings){
            binding.dispose();
        }
        twoWayBindings.clear();
    }

}
