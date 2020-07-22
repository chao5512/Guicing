package demos.guice;

import com.google.common.collect.ImmutableList;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import demos.jackson.JacksonModule;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GuiceInjector {
    public static Collection<Module> makeDefaultStartupModules()
    {
        return ImmutableList.of(
            new PropertiesModule(Arrays.asList("common.runtime.properties", "runtime.properties")),
            new JacksonModule(),
            binder-> {
                binder.bind(Configs.class);
            }
        );
    }

    public static Injector makeStartupInjector()
    {
        return Guice.createInjector(makeDefaultStartupModules());
    }

    public static Injector makeStartupInjectorWithModules(Iterable<? extends Module> modules)
    {
        List<Module> theModules = new ArrayList<>(makeDefaultStartupModules());
        for (Module theModule : modules) {
            theModules.add(theModule);
        }
        return Guice.createInjector(theModules);
    }
}
