package com.bldrei.jsoln.utils;

import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class TestUtil {
//  private TestUtil() {}

  public static void shouldThrow(Class<? extends RuntimeException> exception, Executable action, String errorMessage) {
    var ex = assertThrowsExactly(exception, action);
    assertEquals(errorMessage, ex.getMessage());
  }
}
