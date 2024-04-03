package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.converter.number.BigDecimalConverter;
import com.bldrei.jsoln.converter.number.BigIntegerConverter;
import com.bldrei.jsoln.converter.number.ByteConverter;
import com.bldrei.jsoln.converter.number.DoubleConverter;
import com.bldrei.jsoln.converter.number.FloatConverter;
import com.bldrei.jsoln.converter.number.IntegerConverter;
import com.bldrei.jsoln.converter.number.LongConverter;
import com.bldrei.jsoln.converter.number.ShortConverter;
import com.bldrei.jsoln.util.ClassTree;

import java.util.List;

public final class JsonNumber extends JsonElement {
  private final String numberAsString;

  public JsonNumber(String numberAsString) {
    this.numberAsString = numberAsString;
  }

  public Number getNumericValue(ClassTree classTree) {
    return NUMBER_CONVERTERS.stream()
      .filter(converter -> converter.getType().equals(classTree.rawType()))
      .findAny()
      .map(converter -> converter.convert(numberAsString))
      .orElseThrow(() -> new UnsupportedOperationException("Not implemented numeric class: " + classTree.rawType()));
  }

  private static final List<AbstractConverter<? extends Number>> NUMBER_CONVERTERS = List.of(
    new IntegerConverter(),
    new LongConverter(),
    new DoubleConverter(),
    new FloatConverter(),
    new BigDecimalConverter(),
    new BigIntegerConverter(),
    new ShortConverter(),
    new ByteConverter()
  );
}
