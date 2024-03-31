package config

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer
import org.springframework.boot.test.context.TestComponent
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder

@TestComponent
class MockMvcCustomizer<T : Nothing?> : MockMvcBuilderCustomizer {
    override fun customize(builder: ConfigurableMockMvcBuilder<*>) {
        builder.alwaysDo<T> { result -> result.response.characterEncoding = Charsets.UTF_8.name() }
    }
}
