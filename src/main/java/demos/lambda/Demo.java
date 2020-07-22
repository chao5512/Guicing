package demos.lambda;

import com.google.common.collect.ImmutableList;
import demos.Person;

public class Demo {
    public static void main(String[] args) {

        ImmutableList<Model> of = ImmutableList.of(
            new Model() {
                @Override
                public void config(Person p) {
                    System.out.println("old school");
                }

                @Override
                public String toString() {
                    return "a";
                }
            },
            x -> {
                x.setName("chaos");
                System.out.println(x.getName());
            }
            );
        of.forEach(p -> Model.config(p));
    }
}
