package am.armen181.redirecthttpstohttp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class BeanConfiguration {


    @Bean
    public RestTemplate getRestTemplate() {
        return createRestTemplate();
    }

    private RestTemplate createRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        final MappingJackson2HttpMessageConverter converter = createJacksonConverter();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    private MappingJackson2HttpMessageConverter createJacksonConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_OCTET_STREAM,
                MediaType.TEXT_HTML,
                MediaType.TEXT_PLAIN
        ));
        final MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(Collections.singletonList(mediaType));
        return converter;
    }

}
