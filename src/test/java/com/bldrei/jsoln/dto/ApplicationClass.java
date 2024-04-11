package com.bldrei.jsoln.dto;

import com.bldrei.jsoln.dto.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;

@Getter
@Setter
@ToString
public class ApplicationClass {
  private static String staticField;
  private static Vector staticFieldOfTypeUnsupportedForSerialization;

  private Integer channelId;
  private Optional<String> country;
  private List<String> accountsList;
  private Optional<Set<String>> accountsSet;
  private Optional<LocalDate> expirationDate;
  private Optional<String> emptyval;
  private BigDecimal income;
  private Optional<ApplicationClass> application;
  private Optional<Status> initialStatus;
  private Status currentStatus;
}
