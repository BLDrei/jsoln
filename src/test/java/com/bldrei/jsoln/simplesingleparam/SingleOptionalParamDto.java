package com.bldrei.jsoln.simplesingleparam;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class SingleOptionalParamDto {
  private Optional<String> optionalString;
  private static String staticString;
}
