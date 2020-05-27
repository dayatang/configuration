package yang.yu.configuration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class Configuration3Test {

    @TempDir
    Path tempDir;

    private Configuration instance;

    @BeforeEach
    void setUp() throws Exception {
        Path file = tempDir.resolve("conf.properties");
        Files.write(file, Arrays.asList("birthday=2002-05-11",
                "size=15",
                "closed=true",
                "locked = false",
                "salary=12.5",
                "name=张三",
                "noneValue="));
        instance = Configuration.builder()
                .fromFile(file.toFile())
                .dateFormat("yyyy-MM-dd")
                .build();
    }

    @AfterEach
    void tearDown() {
        System.out.println("Noting to do for now.");
    }

    /**
     * key存在,value存在,应当返回value
     */
    @Test
    void get_string_without_defaultValue_happy() {
        assertThat(instance.getString("name")).isEqualTo("张三");
    }

    /**
     * key存在, value存在,格式正确,应当返回value
     */
    @Test
    void get_int_with_defaultValue_and_with_value() {
        assertThat(instance.getInt("size", 1000)).isEqualTo(15);
    }


}