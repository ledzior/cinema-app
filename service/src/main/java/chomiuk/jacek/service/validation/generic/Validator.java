package chomiuk.jacek.service.validation.generic;

import java.util.Map;
import java.util.stream.Collectors;

public interface Validator<T> {
    Map<String, String> validate(T item);

    static <T> void validate(Validator<T> validator, T item) {
        var errors = validator.validate(item);
        if (!errors.isEmpty()) {
            var message = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ValidatorException("[VALIDATION ERRORS]: " + message);
        }
    }
}
