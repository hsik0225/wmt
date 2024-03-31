package com.hsik.wmt.config

import com.hsik.wmt.utils.Jackson
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration

@Configuration
@ControllerAdvice
class WebConfiguration : DelegatingWebMvcConfiguration() {

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val objectMapper = Jackson.getMapper()
        converters.add(ByteArrayHttpMessageConverter())
        converters.add(StringHttpMessageConverter())
        converters.add(MappingJackson2HttpMessageConverter(objectMapper))
        super.configureMessageConverters(converters)
    }
}
