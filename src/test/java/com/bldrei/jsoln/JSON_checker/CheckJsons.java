package com.bldrei.jsoln.JSON_checker;

import org.junit.jupiter.api.Test;
import org.stringtree.json.ExceptionErrorListener;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONValidatingReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class CheckJsons {

  private static final JSONReader reader = new JSONValidatingReader(new ExceptionErrorListener());

  @Test
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
          IllegalArgumentException.class,
          () -> reader.read(content),
          () -> f.getName() + " should have failed, but didn't"
        );
      }
      else {
        try {
          reader.read(content);
        }
        catch (IllegalArgumentException e) {
          fail(f.getName() + " should be ok, but json reader failed");
        }
      }

    }
  }
}
