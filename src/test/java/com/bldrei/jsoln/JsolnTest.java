package com.bldrei.jsoln;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

public class JsolnTest {

  @Test
  public void testDeserialization() throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    String jsonTxt = """
        {"speed":2,"name":"ansoln"}
        """.trim();
//    Integer x = Jsoln.deserialize(jsonTxt, Integer.class);
  }
}
