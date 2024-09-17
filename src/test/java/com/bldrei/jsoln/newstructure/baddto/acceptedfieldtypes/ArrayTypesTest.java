package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonModelType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayTypesTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(classes = {
    List.class,
    Set.class
  })
  void testOkTypes(Class<?> okType) {
    assertEquals(
      JsonModelType.ARRAY,
      AcceptedFieldTypes.determineFieldJsonDataType(okType)
    );
  }

  @ParameterizedTest
  @ValueSource(classes = {
    Integer[].class,
    int[].class,
    ArrayList.class,
    LinkedList.class,
    Collection.class,
    Iterable.class,
    HashSet.class,
    LinkedHashSet.class,
    TreeSet.class
  })
  void testNOKTypes(Class<?> nokType) {
    shouldThrow(BadDtoException.class,
      () -> AcceptedFieldTypes.determineFieldJsonDataType(nokType),
      "Unsupported field type: " + nokType.getName()
    );
  }
}
