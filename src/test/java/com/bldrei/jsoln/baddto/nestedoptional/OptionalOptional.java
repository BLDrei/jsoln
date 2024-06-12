package com.bldrei.jsoln.baddto.nestedoptional;

import java.util.Optional;

public record OptionalOptional(Optional<Optional<String>> nestedOptional) {
}
