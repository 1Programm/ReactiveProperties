package com.programm.libraries.reactiveproperties;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.Queue;

public class BatchInvoker {

    public interface Strategy {
        void invoke(Runnable runnable);
    }

    public static class InfiniteCycleException extends RuntimeException {
        public InfiniteCycleException(Throwable cause) {
            super("Endless invocation cycle detected.", cause);
        }
    }

    private static RuntimeException addExceptionCause(RuntimeException ex, RuntimeException cause){
        if(cause != null){
            Throwable tail = ex;
            while (tail.getCause() != null){
                tail = ex.getCause();
            }
            tail.initCause(cause);
        }
        return ex;
    }

    public static void setOverrideStrategy(Strategy strategy){
        overrideStrategy = strategy;
    }

    public static final Strategy SWING_INVOKE_LATER_STRATEGY = SwingUtilities::invokeLater;
    public static final Strategy INVOKE_IMMEDIATELY_STRATEGY = Runnable::run;

    private static final int MAX_CYCLE_COUNT = 10;

    private static Strategy overrideStrategy;



    private final Strategy strategy;
    private final Queue<Runnable> runnables = new ArrayDeque<>();
    private final Queue<Runnable> deferredRunnables = new ArrayDeque<>();

    private boolean updateInProgress;

    public BatchInvoker(){
        this(overrideStrategy != null ? overrideStrategy : SWING_INVOKE_LATER_STRATEGY);
    }

    public BatchInvoker(Strategy strategy){
        this.strategy = strategy;
    }

    public void enqueue(Runnable runnable){
        if(updateInProgress){
            if(!deferredRunnables.contains(runnable)){
                deferredRunnables.add(runnable);
            }
            return;
        }

        boolean shouldInvoke = runnables.isEmpty();
        if(!runnables.contains(runnable)){
            runnables.add(runnable);
        }

        if(shouldInvoke){
            enqueueInvoke();
        }
    }

    private void enqueueInvoke(){
        strategy.invoke(this::strategyInvoke);
    }

    private void strategyInvoke(){
        int cycleCount = 0;
        RuntimeException runnableExceptionChain = null;

        while(true){
            updateInProgress = true;
            for(Runnable runnable : runnables){
                try{
                    runnable.run();
                }
                catch (RuntimeException ex){
                    runnableExceptionChain = addExceptionCause(ex, runnableExceptionChain);
                }
            }
            runnables.clear();
            updateInProgress = false;

            if(!deferredRunnables.isEmpty()){
                cycleCount++;
                if(cycleCount > MAX_CYCLE_COUNT){
                    deferredRunnables.clear();
                    throw new InfiniteCycleException(runnableExceptionChain);
                }

                runnables.addAll(deferredRunnables);
                deferredRunnables.clear();
            }
            else {
                break;
            }

            if(runnableExceptionChain != null){
                throw runnableExceptionChain;
            }
        }
    }

}
