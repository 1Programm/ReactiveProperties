package com.programm.libraries.reactiveproperties;

import java.util.*;

public class ListenerManager {

    private static class ListenerPairing {
        private final ObservableValue<?> src;
        private final ChangeListener listener;

        public ListenerPairing(ObservableValue<?> src, ChangeListener listener) {
            this.src = src;
            this.listener = listener;

            src.addListener(listener);
        }

        public void dispose(){
            src.removeListener(listener);
        }
    }

    public final class CompositeListener implements ChangeListener, Runnable {
        private final ObservableValue<?>[] values;
        private Runnable onAnyChange;

        public CompositeListener(ObservableValue<?>[] values) {
            this.values = values;

            for(ObservableValue<?> value : values){
                value.addListener(this);
            }
        }

        @Override
        public void onChange() {
            invoker.enqueue(this);
        }

        @Override
        public void run() {
            if(onAnyChange != null){
                onAnyChange.run();
            }
        }

        public void dispose(){
            for(ObservableValue<?> value : values){
                value.removeListener(this);
            }
        }

        public void with(Runnable onAnyChange){
            this.onAnyChange = onAnyChange;
        }

        public void withAndFire(Runnable onAnyChange){
            with(onAnyChange);
            run();
        }

        public boolean ownsRunnable(Runnable runnable){
            return runnable.equals(onAnyChange);
        }
    }

    public static ListenerManager Immediate(){
        return new ListenerManager(BatchInvoker.INVOKE_IMMEDIATELY_STRATEGY);
    }

    private final List<ListenerPairing> listeners = new ArrayList<>();
    private final List<CompositeListener> compositeListeners = new ArrayList<>();
    private final Map<Receiver<?>, ChangeListener> receiverMapping = new HashMap<>();

    private final BatchInvoker invoker;

    public ListenerManager() {
        this.invoker = new BatchInvoker();
    }

    public ListenerManager(BatchInvoker.Strategy strategy) {
        this.invoker = new BatchInvoker(strategy);
    }

    public <T> void listen(ObservableValue<T> src, ChangeListener listener){
        listeners.add(new ListenerPairing(src, listener));
    }

    public <T> void listen(ObservableValue<T> src, Receiver<T> receiver){
        ChangeListener listenerWrapper = () -> receiver.receive(src.get());
        receiverMapping.put(receiver, listenerWrapper);

        listen(src, listenerWrapper);
    }

    public void listenAndFire(ObservableValue<?> src, ChangeListener listener){
        listen(src, listener);
        listener.onChange();
    }

    public <T> void listenAndFire(ObservableValue<T> src, Receiver<T> receiver){
        listen(src, receiver);
        receiver.receive(src.get());
    }

    public CompositeListener listenAll(ObservableValue<?>... values){
        CompositeListener listener = new CompositeListener(values);
        compositeListeners.add(listener);
        return listener;
    }

    public CompositeListener listenAll(Collection<? extends ObservableValue<?>> values){
        return listenAll((ObservableValue<?>[])values.toArray());
    }

    public void release(ChangeListener listener){
        Iterator<ListenerPairing> i = listeners.iterator();
        while(i.hasNext()){
            ListenerPairing pairing = i.next();

            if(pairing.listener == listener){
                pairing.dispose();
                i.remove();
            }
        }
    }

    public void release(Receiver<?> receiver){
        ChangeListener listenerWrapper = receiverMapping.get(receiver);
        if(listenerWrapper == null) return;

        release(listenerWrapper);
    }

    public void release(ObservableValue<?> observable){
        Iterator<ListenerPairing> i = listeners.iterator();
        while(i.hasNext()){
            ListenerPairing pairing = i.next();

            if(pairing.src == observable){
                pairing.dispose();
                i.remove();
            }
        }
    }

    public void release(Runnable listenAllRunnable){
        Iterator<CompositeListener> i = compositeListeners.iterator();
        while(i.hasNext()){
            CompositeListener listener = i.next();

            if(listener.ownsRunnable(listenAllRunnable)){
                listener.dispose();
                i.remove();
            }
        }
    }

    public void releaseAll(){
        for(ListenerPairing pairing : listeners){
            pairing.dispose();
        }
        listeners.clear();

        for(CompositeListener listener : compositeListeners){
            listener.dispose();
        }

        compositeListeners.clear();
    }
}
