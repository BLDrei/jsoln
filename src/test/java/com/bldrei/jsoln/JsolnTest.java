package com.bldrei.jsoln;

import com.bldrei.jsoln.playground.Application;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsolnTest {

  @Test
  public void deserializeSimpleObjectNoPrettyFormatting() throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ExecutionControl.NotImplementedException, ClassNotFoundException {
    Application application = Jsoln.deserialize("""
    {"country":"EE","channelId":6,"accountsList":["EE02"],"accountsSet":["EE03s"],"income":1300.12}""", Application.class);

    assertNotNull(application);
    assertEquals(6, application.getChannelId());
    assertEquals(Optional.of("EE"), application.getCountry());
    assertEquals(1, application.getAccountsList().size());
    assertEquals("EE02", application.getAccountsList().getFirst());
    assertThrows(RuntimeException.class, () -> application.getAccountsList().add(""));
    assertEquals(1, application.getAccountsSet().size());
    assertTrue(application.getAccountsSet().contains("EE03s"));
    assertThrows(RuntimeException.class, () -> application.getAccountsSet().add(""));
    assertTrue(application.getEmptyval().isEmpty());
    assertTrue(application.getApplication().isEmpty());
  }

  @Test
  public void deserializeSimpleObjectWhitespaceFormatting() throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ExecutionControl.NotImplementedException, ClassNotFoundException {
    Application application = Jsoln.deserialize("""
      {   \t    "country": "EE", \n "channelId": 6  , "accounts" :[ "EE02" ,"empty"], "income" :1300.12 }""", Application.class);

    assertNotNull(application);
    assertEquals(6, application.getChannelId());
    assertEquals(Optional.of("EE"), application.getCountry());
    assertTrue(application.getEmptyval().isEmpty());
    assertTrue(application.getApplication().isEmpty());
  }

  @Test
  public void deserializeRecursiveObject() throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ExecutionControl.NotImplementedException, ClassNotFoundException {
    Application application = Jsoln.deserialize("""
      {"country":"EE","channelId":6,"income":1300.12,"application":{"income":12.3456,"channelId":6}}""", Application.class);

    assertNotNull(application);
    assertEquals(6, application.getChannelId());
    assertEquals(Optional.of("EE"), application.getCountry());
    assertTrue(application.getEmptyval().isEmpty());

    Application innerApplication = application.getApplication().get();
    assertEquals(new BigDecimal("12.3456"), innerApplication.getIncome());
    assertEquals(6, innerApplication.getChannelId());
    assertTrue(innerApplication.getApplication().isEmpty());
  }
}
