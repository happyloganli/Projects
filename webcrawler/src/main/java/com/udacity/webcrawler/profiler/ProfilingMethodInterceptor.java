package com.udacity.webcrawler.profiler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Duration;
import java.util.Objects;

/**
 * A method interceptor that checks whether {@link Method}s are annotated with the {@link Profiled}
 * annotation. If they are, the method interceptor records how long the method invocation took.
 */
final class ProfilingMethodInterceptor implements InvocationHandler {

  private final Clock clock;
  private final ProfilingState state;
  private final Object delegate;

  // TODO: You will need to add more instance fields and constructor arguments to this class.
  ProfilingMethodInterceptor(Object delegate, ProfilingState state, Clock clock) {

    this.clock = Objects.requireNonNull(clock);
    this.delegate = Objects.requireNonNull(delegate);
    this.state = Objects.requireNonNull(state);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (method.isAnnotationPresent(Profiled.class)) {
      long startTime = clock.millis();
      try {
        Object result = method.invoke(delegate, args);
        long endTime = clock.millis();
        long duration = endTime - startTime;
        state.record(delegate.getClass(), method, Duration.ofMillis(duration));
        return result;
      } catch (Throwable throwable) {
        long endTime = clock.millis();
        long duration = endTime - startTime;
        state.record(delegate.getClass(), method, Duration.ofMillis(duration));
        Throwable originalException = throwable.getCause();
        if (originalException != null) {
          throw originalException;
        } else {
          throw throwable;
        }
      }
    }
    try {
      return method.invoke(delegate, args);
    }catch (Throwable throwable){
      throw throwable;
    }

  }
}















