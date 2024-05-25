package com.bldrei.jsoln.simplesingleparam;

import java.util.Optional;

public record SingleOptionalParamDto(Optional<String> optionalString) {
  private final static String staticString = "";
}
