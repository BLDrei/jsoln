package com.bldrei.jsoln;

import com.bldrei.jsoln.cache.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public abstract class AbstractTest {

  @BeforeEach
  public void clearCache() {
    Cache.clear();
  }

  protected void shouldThrow(Class<? extends RuntimeException> exception, Executable action, String errorMessage) {
    var ex = assertThrowsExactly(exception, action);
    assertEquals(errorMessage, ex.getMessage());
  }

  @Deprecated
  protected void shouldThrow(Class<? extends RuntimeException> exception, Executable action) {
    assertThrowsExactly(exception, action);
  }

  protected void shouldThrow(RuntimeException exception, Executable action) {
    shouldThrow(exception.getClass(), action, exception.getMessage());
  }
}
