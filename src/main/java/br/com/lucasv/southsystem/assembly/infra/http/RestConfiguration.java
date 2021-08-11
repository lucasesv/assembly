package br.com.lucasv.southsystem.assembly.infra.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * General configuration for the REST API
 * 
 * @author Lucas Vasconcelos
 *
 */
@Configuration
public class RestConfiguration {

  /**
   * Configure the Swagger documentation
   * 
   * @return The {@link OpenAPI} configured.
   */
  @Bean
  public OpenAPI assemblySession() {
    return new OpenAPI()
        .info(new Info().title("Assembly Session API")
            .description("Assembly sessions management")
            .version("v0.1"));
  }
  
}
