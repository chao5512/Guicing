package demos;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import java.util.OptionalInt;
import java.util.stream.Stream;

public class NamedInjectDemo {
    public static void main(String[] args) {
        System.out.println("test");
        Injector injector = Guice.createInjector(new NamedInjectModule());
        Stream.of(injector.getAllBindings().keySet())
              .forEach(System.out::println);
        //获取这个integer
        System.out.println(injector.getInstance(Key.get(Integer.class, Names.named("service.port"))));
        injector.getInstance(NamedInjectServer.class).sout();
    }


}
class NamedInjectModule extends AbstractModule {
    @Override
    protected void configure() {
        // 绑定一个integer
        binder().bindConstant().annotatedWith(Names.named("service.port")).to(8023);
    }
}
class NamedInjectServer {
    private int port;

    @Inject
    public NamedInjectServer(@Named("service.port") int port) {
        this.port =  port;
    }

    public void sout(){
        System.out.println(OptionalInt.of(port).orElse(0));
    }
}
