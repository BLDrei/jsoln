package com.bldrei.jsoln.serialize.simple;

import java.util.List;
import java.util.Set;
import java.util.Map;

public record CollectionDto(List<Integer> ages, Map<String, List<String>> childNames) {
}
