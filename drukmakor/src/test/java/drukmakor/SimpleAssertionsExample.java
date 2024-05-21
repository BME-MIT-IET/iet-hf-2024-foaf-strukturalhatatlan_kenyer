package drukmakor;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.assertj.core.api.Assertions.assertThat; 

import org.junit.jupiter.api.Test;

class SimpleAssertionsExampleTest {

  @Test
  void aFewSimpleAssertions() {
        assertTrue(true);
    assertThat("The Lord of the Rings").isNotNull()   
                                       .startsWith("The") 
                                       .contains("Lord") 
                                       .endsWith("Rings"); 
  }
  
  @Test
  void aFewSimpleAssertions2() {
        assertTrue(true);
  }

}
