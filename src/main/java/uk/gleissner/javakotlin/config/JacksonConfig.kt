package uk.gleissner.javakotlin.config

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class JacksonConfig {

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper().findAndRegisterModules()

    companion object {
        inline fun <reified T> ObjectMapper.fromJson(src: String): T = readValue(src, object : TypeReference<T>() {})
        fun ObjectMapper.jsonOf(any: Any): String = writeValueAsString(any)
    }
}
