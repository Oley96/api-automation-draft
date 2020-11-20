package conditions;

import lombok.experimental.UtilityClass;

/**
 * @author Vladimir Oleynik
 */
@UtilityClass
public class Conditions {

    public StatusCodeCondition statusCode(int code) {
        return new StatusCodeCondition(code);
    }
}
