package demos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonDemo {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper
            .configure(SerializationFeature.INDENT_OUTPUT, false)
            .configure(MapperFeature.AUTO_DETECT_GETTERS, false)
            .configure(MapperFeature.AUTO_DETECT_SETTERS, false);
        Person person = new Person();
        System.out.println(objectMapper.writeValueAsString(person));

        String json = "{\"name\":\"chaos\"}";
        Person person1 = objectMapper.readValue(json, Person.class);
        System.out.println(person1.getName());
    }
}
