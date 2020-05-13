package strategy.validate;

import java.io.IOException;

public interface ValidationStrategy<T> {
  
  ValidationResult<Boolean> isValid(T subject) throws IOException;
}