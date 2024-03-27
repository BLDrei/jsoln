package com.bldrei.jsoln;

import com.bldrei.jsoln.playground.Application;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsolnTest {

  @Test
  public void deserializeSimpleObjectNoPrettyFormatting() throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ExecutionControl.NotImplementedException {
    Application application = Jsoln.deserialize("{\"country\":\"EE\",\"channelId\":6,\"income\":1300.12}", Application.class);

    assertNotNull(application);
    assertEquals(6, application.getChannelId());
    assertEquals(Optional.of("EE"), application.getCountry());
    assertTrue(application.getEmptyval().isEmpty());
  }

  @Test
  public void deserializeSimpleObjectWhitespaceFormatting() throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ExecutionControl.NotImplementedException {
    Application application = Jsoln.deserialize("{   \t    \"country\": \"EE\", \n \"channelId\": 6  , \"income\" :1300.12 }", Application.class);

    assertNotNull(application);
    assertEquals(6, application.getChannelId());
    assertEquals(Optional.of("EE"), application.getCountry());
    assertTrue(application.getEmptyval().isEmpty());
  }
}
