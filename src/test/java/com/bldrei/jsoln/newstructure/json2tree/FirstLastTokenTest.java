package com.bldrei.jsoln.newstructure.json2tree;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.exception.JsonSyntaxException;
import com.bldrei.jsoln.util.DeserializeUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
      () -> DeserializeUtil.toJsonTree(json));
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
    DeserializeUtil.toJsonTree(json);
  }

}
