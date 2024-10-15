package com.bldrei.jsoln;

import com.bldrei.jsoln.cache.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public abstract class AbstractTest {

  @BeforeEach
  public void clearCache() {
    Cache.clear();
  }

  protected <E extends Exception> E shouldThrow(Class<E> exception, Executable action, String errorMessage) {
    var ex = assertThrowsExactly(exception, action);
    //assertEquals(errorMessage, ex.getMessage()); //temporarily disable error message check
    return ex;
  }

  protected <E extends Exception> E shouldThrow(Class<E> exception, Executable action) {
    return assertThrowsExactly(exception, action);
  }
}
