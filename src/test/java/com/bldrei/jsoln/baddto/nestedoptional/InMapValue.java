package com.bldrei.jsoln.baddto.nestedoptional;

import java.util.Map;
import java.util.Optional;

public record InMapValue(Map<String, Optional<String>> map) {
}
