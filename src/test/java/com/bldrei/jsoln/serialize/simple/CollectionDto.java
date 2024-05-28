package com.bldrei.jsoln.serialize.simple;

import java.util.List;
import java.util.Set;
import java.util.Map;

public record CollectionDto(List<Integer> ages, Set<String> names, Map<String, List<String>> childNames) {
}
