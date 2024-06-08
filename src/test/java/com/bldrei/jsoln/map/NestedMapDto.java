package com.bldrei.jsoln.map;

import java.util.Map;

public record NestedMapDto(Map<String, Map<String, String>> translationsByLanguage) {
}
