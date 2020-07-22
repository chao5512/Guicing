package demos;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * 想要的json型式
 * {
 *     "name": "abc",
 *     "city": "xyz"
 * }
 *
 * 对应的
 */
public class JacksonMixinDemo {

    /* 3rd party */
    public static class Employee {
        public String name;
        //@JsonUnwrapped  在不能更改Emplyee的前提下，直接在这个字段上加unwrapped注解显然是不现实的
        public Address address;
    }

    /* 3rd party */
    public static class Address {
        public String city;
    }

    /* Jackon Module for Employee */
    public static class EmployeeModule extends SimpleModule {
        abstract class EmployeeMixin {
            @JsonUnwrapped
            public Address address;
        }

        //abstract class EmployeeAdd {
        //    public String sex;
        //}

        public EmployeeModule() {
            super("Employee");
            // 在setup中写这句是没用的
            setMixInAnnotation(Employee.class, EmployeeMixin.class);
            //setMixInAnnotation(Employee.class, EmployeeAdd.class);
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        Employee emp = new Employee();
        emp.name = "Bob";
        emp.address = new Address();
        emp.address.city = "New York";


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new EmployeeModule());

        System.out.println(mapper.writeValueAsString(emp));
        Employee e = mapper.readValue("{\"name\":\"Bob\",\"city\":\"New York\"}", Employee.class);
        System.out.println(e.name);
        System.out.println(e.address.city);

    }
}
