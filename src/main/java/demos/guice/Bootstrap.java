package demos.guice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Injector;
import com.google.inject.Key;
import demos.Person;
import demos.guice.annotations.Json;
import java.util.stream.Stream;

public class Bootstrap {
    public static void main(String[] args) {
        Injector injector = GuiceInjector.makeStartupInjector();
        Configs instance = injector.getInstance(Configs.class);
        instance.sout();
        ObjectMapper objectMapper = injector.getInstance(Key.get(ObjectMapper.class, Json.class));

        Person person = new Person();
        person.setName("chao");
        person.setSex("man");

        Stream.of(person)
              .map(p -> {
                  System.out.println(p.getName());
                  return p.getName();
              })
              .forEach(System.out::println);
        try {
            System.out.println(objectMapper.writeValueAsString(person));
        } catch (JsonProcessingException e) {
            System.out.println("json error :" + e);
        }

    }
}
