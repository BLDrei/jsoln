package com.bldrei.jsoln.playground;

import java.util.Optional;

public record Application (
  int channelId,
  Optional<String> country,
  Optional<String> emptyval
) {}
