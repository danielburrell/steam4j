package uk.co.solong.steam4j.mockapi.config;

import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import uk.co.solong.application.annotations.RootConfiguration;
import uk.co.solong.application.config.PropertyPlaceholderConfig;

@Configuration
@RootConfiguration
@Import({ WebConfig.class, PropertyPlaceholderConfig.class, RepositoryRestMvcAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class,
    SpringDataWebAutoConfiguration.class, WebMvcAutoConfiguration.class, EmbeddedServletContainerAutoConfiguration.class,
    DispatcherServletAutoConfiguration.class, ServerPropertiesAutoConfiguration.class, ApplicataionControllerConfig.class })
public class Config {

}
