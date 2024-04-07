package com.bldrei.jsoln.dto;

import com.bldrei.jsoln.dto.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public record ApplicationRecord(
  Integer channelId,
  Optional<String> country,
  List<String> accountsList,
  Optional<Set<String>> accountsSet,
  Optional<LocalDate> expirationDate,
  Optional<String> emptyval,
  BigDecimal income,
  Optional<ApplicationRecord> application,
  Optional<Status> initialStatus,
  Status currentStatus
) {}
