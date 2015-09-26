package uk.co.solong.steam4j.mockapi.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.co.solong.steam4j.mockapi.controllers.api.APIController;

@Configuration
public class ApplicataionControllerConfig {

   
    
    @Bean
    public APIController applicationController() {
        APIController applicationController =  new APIController();
        
        return applicationController;
    }
}
