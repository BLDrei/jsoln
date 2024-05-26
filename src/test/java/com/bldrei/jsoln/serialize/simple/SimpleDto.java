package com.bldrei.jsoln.serialize.simple;

import java.util.Optional;

public record SimpleDto(String s, Integer i, Boolean b, Optional<String> str) {
}
