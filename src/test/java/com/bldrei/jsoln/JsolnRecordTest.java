package com.bldrei.jsoln;

import com.bldrei.jsoln.dto.ApplicationRecord;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsolnRecordTest extends AbstractTest {

  @Test
  void deserializeSimpleObjectNoPrettyFormatting() {
    ApplicationRecord application = Jsoln.deserialize("""
      {"country":"EE","channelId":6,"accountsList":["EE02"],"accountsSet":["EE03s"],"expirationDate":"2024-03-31","income":1300.12,"initialStatus":"ERROR","currentStatus":"IN_PROGRESS"}""", ApplicationRecord.class);
    var accountsList = application.accountsList();
    var accountsSet = application.accountsSet().orElseThrow();

    assertNotNull(application);
    assertEquals(6, application.channelId());
    assertEquals(Optional.of("EE"), application.country());
    assertEquals(1, accountsList.size());
    assertEquals("EE02", accountsList.getFirst());
    assertThrows(RuntimeException.class, () -> accountsList.add(""));
    assertThrows(RuntimeException.class, () -> accountsList.remove(""));
    assertEquals(1, accountsSet.size());
    assertTrue(accountsSet.contains("EE03s"));
    assertThrows(RuntimeException.class, () -> accountsSet.add(""));
    assertThrows(RuntimeException.class, () -> accountsSet.remove(""));
    assertEquals(LocalDate.of(2024, Month.MARCH, 31), application.expirationDate().get());
    assertTrue(application.emptyval().isEmpty());
    assertTrue(application.application().isEmpty());
  }

  @Test
  void deserializeSimpleObjectWhitespaceFormatting() {
    ApplicationRecord application = Jsoln.deserialize("""
      {   \t    "country": "EE", \n "channelId": 6  , "accountsList" :[ "EE02" ,"empty"], "income" :1300.12 , "currentStatus" :"ERROR" }""", ApplicationRecord.class);

    assertNotNull(application);
    assertEquals(6, application.channelId());
    assertEquals(Optional.of("EE"), application.country());
    assertTrue(application.emptyval().isEmpty());
    assertTrue(application.application().isEmpty());
  }

  @Test
  void deserializeRecursiveObject() {
    ApplicationRecord application = Jsoln.deserialize("""
      {"country":"EE","accountsList":["EE02","empty"],"channelId":6,"income":1300.12,"application":{"income":12.3456,"channelId":6,"accountsList":[],"currentStatus":"ERROR"},"currentStatus":"OK"}""", ApplicationRecord.class);

    assertNotNull(application);
    assertEquals(6, application.channelId());
    assertEquals(Optional.of("EE"), application.country());
    assertTrue(application.emptyval().isEmpty());

    ApplicationRecord innerApplication = application.application().get();
    assertEquals(new BigDecimal("12.3456"), innerApplication.income());
    assertEquals(6, innerApplication.channelId());
    assertTrue(innerApplication.application().isEmpty());
  }
}
