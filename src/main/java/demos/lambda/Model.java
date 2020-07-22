package demos.lambda;

import demos.Person;

public interface Model {
    static void config(Model p) {
    }

    void config(Person p);
}
