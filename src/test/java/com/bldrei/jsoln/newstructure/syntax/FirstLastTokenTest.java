package com.bldrei.jsoln.newstructure.syntax;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FirstLastTokenTest extends AbstractTest {

  @Disabled
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
    shouldThrow(IllegalArgumentException.class,
      () -> Jsoln.deserialize(json, DummyDTO.class));
  }

//  @ParameterizedTest
//  @ValueSource(strings = {
//    "{}",
//    "\t { } \t ",
//    "[]",
//    "\t [ ] \t ",
//    """
//      {"foo": "bar"}
//      """,
//    """
//      ["foo"]
//      """
//  })
//  void jsonIsWrappedIntoBracketsOrCurlyBraces_ok(String json) {
//    Jsoln.deserialize(json, List.class); //TODO: deserialization straight into arrays is not implemented yet
//  }

  public record DummyDTO() {}
}
