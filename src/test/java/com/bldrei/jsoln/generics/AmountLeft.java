package com.bldrei.jsoln.generics;

public record AmountLeft<T>(
  Long applId,
  T limit
) {}
