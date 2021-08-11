package br.com.lucasv.southsystem.assembly.infra.http;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * General configuration for the REST API
 * 
 * @author Lucas Vasconcelos
 *
 */
@Configuration
@EnableFeignClients
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
  
  /**
   * Configure the MessageSource to use custom messages.
   * 
   * @return The MessageSource configured to use custom messages.
   */
  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();

    messageSource.setBasename("classpath:messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
  
  /**
   * Configure the bean validators to also use the custom.
   * MessageSource configured.
   * 
   * @return The bean configuration to use custom MessageSource.
   */
  @Bean
  public LocalValidatorFactoryBean getValidator() {
      LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
      bean.setValidationMessageSource(messageSource());
      return bean;
  }
}
