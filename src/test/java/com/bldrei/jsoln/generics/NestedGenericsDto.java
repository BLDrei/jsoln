package com.bldrei.jsoln.generics;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record NestedGenericsDto(Optional<List<Map<String, String>>> nonsense) {
}
