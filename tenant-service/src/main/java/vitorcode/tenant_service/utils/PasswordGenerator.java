package vitorcode.tenant_service.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PasswordGenerator {

    public String generate() {

        String upperCaseLetters = RandomStringUtils.secure()
                .next(2, 65, 91, true, true);

        String lowerCaseLetters = RandomStringUtils.secure()
                .next(2, 97, 123, true, true);

        String numbers = RandomStringUtils.secure()
                .nextNumeric(2);

        String totalChars = RandomStringUtils.secure()
                .nextAlphanumeric(2);

        String combinedChars = upperCaseLetters
                .concat(lowerCaseLetters)
                .concat(numbers)
                .concat(totalChars);

        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());

        Collections.shuffle(pwdChars);

        return pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
