package com.bldrei.jsoln.baddto.nestedoptional;

import java.util.Map;
import java.util.Optional;

public record InMapKey(Map<Optional<String>, Integer> map) {
}
