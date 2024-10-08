package com.bldrei.jsoln.newstructure.syntax;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsonSyntaxException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

class FirstLastTokenTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(strings = {
    "{]",
    "[}",
    "",
    """
      "foo": "bar"
      """,
    """
      {"foo": "bar"]
      """,
    """
      ["foo": "bar"}
      """
  })
  void jsonIsNotWrappedIntoBracketsOrCurlyBraces_shouldThrowException(String json) {
    shouldThrow(JsonSyntaxException.class,
      () -> Jsoln.deserialize(json, DummyDTO.class));
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "{}",
    "\t { } \t ",
    "[]",
    "\t [ ] \t ",
    """
      {"foo": "bar"}
      """,
    """
      ["foo"]
      """
  })
  void jsonIsWrappedIntoBracketsOrCurlyBraces_ok(String json) {
    Jsoln.deserialize(json, List.class);
  }

  public record DummyDTO() {}
}
