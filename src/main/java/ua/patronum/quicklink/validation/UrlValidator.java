package ua.patronum.quicklink.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    private static final String URL_PATTERN =
            "^((https?|ftp)://)?(www\\d?\\.)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,9}(/[a-zA-Z0-9-._?,'+&%$#=~]*)?";

    @Override
    public void initialize(ValidUrl constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        Pattern pattern = Pattern.compile(URL_PATTERN);
        Matcher matcher = pattern.matcher(value);

        return matcher.matches();
    }
}
