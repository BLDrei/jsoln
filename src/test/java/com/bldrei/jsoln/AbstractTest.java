package com.bldrei.jsoln;

import com.bldrei.jsoln.cache.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public abstract class AbstractTest {

  @BeforeEach
  public void clearCache() {
    Cache.classDeserializationCache.clear();
    Cache.recordDeserializationCache.clear();
  }

  public static void shouldThrow(Class<? extends RuntimeException> exception, Executable action, String errorMessage) {
    var ex = assertThrowsExactly(exception, action);
    assertEquals(errorMessage, ex.getMessage());
  }

  public static void shouldThrow(RuntimeException exception, Executable action) {
    shouldThrow(exception.getClass(), action, exception.getMessage());
  }
}
