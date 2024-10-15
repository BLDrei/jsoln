package com.bldrei.jsoln.JSON_checker;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class CheckJsons {

  @Test
  @Disabled
  void checkJsonsWithOfficialJSON_checker() throws IOException {
    var files = new File("src/test/resources/JSON_checker/test").listFiles();
    for (File f : files) {
      boolean shouldFail = switch (f.getName()) {
        case String s when s.startsWith("fail") && s.endsWith(".json") -> true;
        case String s when s.startsWith("pass") && s.endsWith(".json") -> false;
        default -> throw new IllegalArgumentException("What is this file doing here?");
      };

      String content = String.join("\n", Files.readAllLines(f.toPath()));
      if (shouldFail) {
        assertThrows(
          JsonParseException.class,
          () -> new ObjectMapper().readTree(content),
          () -> f.getName() + " should have failed, but didn't"
        );
      }
      else {
        try {
          new ObjectMapper().readTree(content);
        }
        catch (JsonParseException e) {
          fail(f.getName() + " should be ok, but json reader failed");
        }
      }

    }
  }
}
