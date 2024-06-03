package com.bldrei.jsoln.cache;

import com.bldrei.jsoln.converter.BooleanConverter;
import com.bldrei.jsoln.converter.number.BigDecimalConverter;
import com.bldrei.jsoln.converter.number.BigIntegerConverter;
import com.bldrei.jsoln.converter.number.ByteConverter;
import com.bldrei.jsoln.converter.number.DoubleConverter;
import com.bldrei.jsoln.converter.number.FloatConverter;
import com.bldrei.jsoln.converter.number.IntegerConverter;
import com.bldrei.jsoln.converter.number.LongConverter;
import com.bldrei.jsoln.converter.number.NumberConverter;
import com.bldrei.jsoln.converter.number.ShortConverter;
import com.bldrei.jsoln.converter.text.EnumConverter;
import com.bldrei.jsoln.converter.text.LocalDateConverter;
import com.bldrei.jsoln.converter.text.LocalDateTimeConverter;
import com.bldrei.jsoln.converter.text.StringConverter;
import com.bldrei.jsoln.converter.text.TextConverter;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConvertersCache {
  private ConvertersCache() {}

  private static final Map<Class<?>, TextConverter<?>> textConvertersCache = Map.of(
    String.class, new StringConverter(),
    LocalDate.class, new LocalDateConverter(),
    LocalDateTime.class, new LocalDateTimeConverter()
  );
  private static final Map<Class<?>, TextConverter<?>> enumConvertersCache = new HashMap<>();
  private static final Map<Class<?>, NumberConverter<?>> numberConvertersCache = Map.of(
    Integer.class, new IntegerConverter(),
    Long.class, new LongConverter(),
    Double.class, new DoubleConverter(),
    Float.class, new FloatConverter(),
    BigDecimal.class, new BigDecimalConverter(),
    BigInteger.class, new BigIntegerConverter(),
    Short.class, new ShortConverter(),
    Byte.class, new ByteConverter()
  );
  @Getter
  private static final BooleanConverter booleanConverter = new BooleanConverter();

  @SuppressWarnings("unchecked")
  public static <T> Optional<TextConverter<T>> getTextConverter(Class<T> clazz) {
    var converter = clazz.isEnum()
      ? enumConvertersCache.computeIfAbsent(clazz, ecl -> new EnumConverter<>(clazz))
      : textConvertersCache.get(clazz);
    return Optional.ofNullable((TextConverter<T>) converter);
  }

  @SuppressWarnings("unchecked")
  public static <N> Optional<NumberConverter<N>> getNumberConverter(Class<N> clazz) {
    return Optional.ofNullable((NumberConverter<N>) numberConvertersCache.get(clazz));
  }
}
