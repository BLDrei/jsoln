package com.bldrei.jsoln.playground;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Setter
@ToString
public class Application {
  private int channelId;
  private Optional<String> country;
  private Optional<String> emptyval;
}
