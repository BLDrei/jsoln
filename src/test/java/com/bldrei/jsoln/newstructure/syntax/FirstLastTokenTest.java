package com.bldrei.jsoln.newstructure.syntax;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
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
    shouldThrow(JsonParseException.class,
      () -> Jsoln.deserialize(json, DummyDTO.class));
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "[]",
    "\t [ ] \t ",
    """
      ["foo"]
      """
  })
  void jsonIsWrappedIntoBracketsOrCurlyBraces_ok_list(String json) {
    Jsoln.deserialize(json, new TypeReference<List<String>>() {});
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "{}",
    "\t { } \t ",
    """
      {"foo": "bar"}
      """
  })
  void jsonIsWrappedIntoBracketsOrCurlyBraces_ok_record(String json) {
    Jsoln.deserialize(json, DummyDTO.class);
  }

  public record DummyDTO() {}
}
