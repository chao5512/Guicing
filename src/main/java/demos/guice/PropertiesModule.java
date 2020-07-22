package demos.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

public class PropertiesModule implements Module {

    private final List<String> propertiesFiles;

    /**
     * resource目录下的properties文件名列表
     *
     * @param propertiesFiles
     */
    public PropertiesModule(List<String> propertiesFiles) {
        this.propertiesFiles = propertiesFiles;
    }

    /**
     * 从配置文件load properties
     *
     * @param binder
     */
    @Override
    public void configure(Binder binder) {
        final Properties fileProps = new Properties();
        Properties systemProps = System.getProperties();

        Properties props = new Properties(fileProps);
        props.putAll(systemProps);

        for (String propertiesFile : propertiesFiles) {
            InputStream stream = ClassLoader.getSystemResourceAsStream(propertiesFile);
            try {
                if (stream == null) {
                    // 默认使用resoure下的配置文件，如果配置文件不存在，使用系统配置项中的指定配置项的值作为配置文件
                    File workingDirectoryFile = new File(systemProps.getProperty("druid.properties.file", propertiesFile));
                    if (workingDirectoryFile.exists()) {
                        stream = new BufferedInputStream(new FileInputStream(workingDirectoryFile));
                    }
                }

                if (stream != null) {
                    System.out.println("Loading properties from " + propertiesFile);
                    try (final InputStreamReader in = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                        fileProps.load(in);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("This can only happen if the .exists() call lied.");
            } finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (IOException e) {
                    System.out.println("error when close!");
                    e.printStackTrace();
                }
            }
        }

        binder.bind(Properties.class).toInstance(props);
    }
}
