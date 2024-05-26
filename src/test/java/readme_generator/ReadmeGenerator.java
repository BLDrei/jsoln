package readme_generator;

import com.bldrei.jsoln.converter.text.TextConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ReadmeGenerator {


  @Test
  public void generateReadme() {
    System.out.println("The following text converters are used:");
    var textConverters = TextConverter.class.getPermittedSubclasses();
    assertEquals(4, textConverters.length);
    for (Class<?> converter : textConverters) {
      System.out.println(converter.getSimpleName());
    }
  }
}
