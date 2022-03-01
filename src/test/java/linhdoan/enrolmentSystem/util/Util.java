package linhdoan.enrolmentSystem.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Util {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
