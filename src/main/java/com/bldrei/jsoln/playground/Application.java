package com.bldrei.jsoln.playground;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@ToString
public class Application {
  private Integer channelId;
  private Optional<String> country;
  private List<String> accountsList;
//  private Optional<Set<String>> accountsSet;
  private Optional<LocalDate> expirationDate;
  private Optional<String> emptyval;
  private BigDecimal income;
  private Optional<Application> application;
}
