package com.bldrei.jsoln.playground;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@ToString
public class Application {
  private Integer channelId;
  private Optional<String> country;
  private Optional<String> emptyval;
  private BigDecimal income;
  private Optional<Application> application;
}
