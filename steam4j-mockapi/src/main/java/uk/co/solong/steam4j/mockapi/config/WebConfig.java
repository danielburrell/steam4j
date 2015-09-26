package uk.co.solong.steam4j.mockapi.config;

import java.io.IOException;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    private ResourceProperties resourceProperties = new ResourceProperties();

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/").setViewName("forward:index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Integer cachePeriod = resourceProperties.getCachePeriod();
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");

        registry.addResourceHandler("/**").addResourceLocations("classpath:/assets/index.html").setCachePeriod(cachePeriod).resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        return location.exists() && location.isReadable() ? location : null;
                    }
                });
    }
}