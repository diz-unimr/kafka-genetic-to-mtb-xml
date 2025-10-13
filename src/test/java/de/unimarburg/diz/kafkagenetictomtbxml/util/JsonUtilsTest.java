package de.unimarburg.diz.kafkagenetictomtbxml.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonUtilsTest {

    @Test
    void shouldReturnStringValue() throws Exception {

        var json = """
                {
                    "test_string": "Test",
                    "test_integer": 1,
                    "test_boolean": true
                }
                """;

        assertThat(JsonUtils.extractFromJson(json, "test_string", String.class)).isEqualTo("Test");
    }

    @Test
    void shouldReturnStringValueFromInteger() throws Exception {

        var json = """
                {
                    "test_string": "Test",
                    "test_integer": 1,
                    "test_boolean": true
                }
                """;

        assertThat(JsonUtils.extractFromJson(json, "test_integer", String.class)).isEqualTo("1");
    }

    @Test
    void shouldReturnStringValueFromBoolean() throws Exception {

        var json = """
                {
                    "test_string": "Test",
                    "test_integer": 1,
                    "test_boolean": true
                }
                """;

        assertThat(JsonUtils.extractFromJson(json, "test_boolean", String.class)).isEqualTo("true");
    }

    @Test
    void shouldReturnIntegerValue() throws Exception {

        var json = """
                {
                    "test_string": "Test",
                    "test_integer": 1,
                    "test_boolean": true
                }
                """;

        assertThat(JsonUtils.extractFromJson(json, "test_integer", Integer.class)).isEqualTo(1);
    }

    @Test
    void shouldReturnBooleanValue() throws Exception {

        var json = """
                {
                    "test_string": "Test",
                    "test_integer": 1,
                    "test_boolean": true
                }
                """;

        assertThat(JsonUtils.extractFromJson(json, "test_boolean", Boolean.class)).isEqualTo(true);
    }

    @Test
    void shouldReturnBooleanValueAsInteger() throws Exception {

        var json = """
                {
                    "test_string": "Test",
                    "test_integer": 1,
                    "test_boolean": true
                }
                """;

        assertThat(JsonUtils.extractFromJson(json, "test_boolean", Integer.class)).isEqualTo(1);
    }

    @Test
    void shouldReturnIntegerValueAsBoolean() throws Exception {

        var json = """
                {
                    "test_string": "Test",
                    "test_integer": 1,
                    "test_boolean": true
                }
                """;

        assertThat(JsonUtils.extractFromJson(json, "test_integer", Boolean.class)).isEqualTo(true);
    }

    @Test
    void shouldReturnStringValueAsInteger() throws Exception {

        var json = """
                {
                    "test_string": "42",
                    "test_integer": 1,
                    "test_boolean": true
                }
                """;

        assertThat(JsonUtils.extractFromJson(json, "test_string", Integer.class)).isEqualTo(42);
    }

    @Test
    void shouldReturnStringValueAsBoolean() throws Exception {

        var json = """
                {
                    "test_string": "true",
                    "test_integer": 1,
                    "test_boolean": true
                }
                """;

        assertThat(JsonUtils.extractFromJson(json, "test_string", Boolean.class)).isEqualTo(true);
    }

}
