package yang.yu.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class Configuration2Test {

    @TempDir
    Path tempDir;

    /**
     * key存在,value存在,应当返回value
     */
    @Test
    public void get_string_without_defaultValue_happy() throws IOException {
        Path file = tempDir.resolve("conf.properties");
        Files.write(file, Arrays.asList("birthday=2002-05-11",
                "size=15",
                "closed=true",
                "locked = false",
                "salary=12.5",
                "name=张三",
                "noneValue="));
        Configuration instance = Configuration.builder()
                .fromFile(file.toFile())
                .dateFormat("yyyy-MM-dd")
                .build();
        assertThat(instance.getString("name")).isEqualTo("张三");
    }

    /**
     * key存在, value存在,格式正确,应当返回value
     */
    @Test
    public void get_int_with_defaultValue_and_with_value() throws IOException {
        Path file = tempDir.resolve("conf.properties");
        Files.write(file, Arrays.asList("birthday=2002-05-11",
                "size=15",
                "closed=true",
                "locked = false",
                "salary=12.5",
                "name=张三",
                "noneValue="));

        Configuration instance = Configuration.builder()
                .fromFile(file.toFile())
                .dateFormat("yyyy-MM-dd")
                .build();
        assertThat(instance.getInt("size", 1000)).isEqualTo(15);
    }

}