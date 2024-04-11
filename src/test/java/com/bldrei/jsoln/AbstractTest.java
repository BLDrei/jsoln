package com.bldrei.jsoln;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class AbstractTest {

  @BeforeEach
  public void clearCache() {
    //todo: clear cache
  }

  public static void shouldThrow(Class<? extends RuntimeException> exception, Executable action, String errorMessage) {
    var ex = assertThrowsExactly(exception, action);
    assertEquals(errorMessage, ex.getMessage());
  }
}
