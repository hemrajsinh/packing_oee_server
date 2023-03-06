package in.sparklogic.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;


@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(
//        prePostEnabled = true,  // Enables @PreAuthorize and @PostAuthorize
//        securedEnabled = true // Enables @Secured 
// )
public class AppConfig extends WebMvcConfigurerAdapter {

	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/configuration/ui", "/swagger-resources/configuration/ui");
        
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }	
    
    @Bean
    public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2)
          .apiInfo(apiEndPointsInfo())
          .select()
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder()
        	.title("SPARKLOGIC SERVICE")
            .description("")
//            .contact(new Contact("SPARKLOGIC SERVICE", "www.sparklogic.in", "sparklogicindia@gmail.com"))
//            .license("Apache 2.0")
//            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0.0")
            .build();
    }
    

    private List<SecurityReference> defaultAuth() { 
        AuthorizationScope authorizationScope = new springfox.documentation.service.AuthorizationScope("global", "accessEverything"); 
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
        authorizationScopes[0] = authorizationScope; 
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
    }
    
    @Bean 
    SecurityConfiguration security() {
        return new SecurityConfiguration(null, null, null, null, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYSIsInVzZXIiOnsic3RJbnNVc2VyIjoic2EiLCJkdEluc0RhdGUiOjE2NzcyNTY1MzEwMTMsInN0SW5zVGVybSI6IiIsImlkIjoxLCJzdFVzZXJOYW1lIjoic2EiLCJzdFVzZXJUeXBlIjoiU1VQRVJfQURNSU4iLCJzdFBhc3N3b3JkIjoic2EiLCJzdE5hbWUiOiJzYSIsInN0RW1haWwiOiJzYUBzYS5jb20iLCJmbGdJc0VtYWlsVmVyaWZpZWQiOmZhbHNlLCJzdExvZ28iOm51bGwsInN0SW1hZ2UiOm51bGwsInN0SW1hZ2VGb3JtYXR0ZWROYW1lIjoiMTY1XzhlYWQ3NTE2LTE4OTUtNDk4Ny04YTlkLTc0NDczMjEzYzUwOS5qcGciLCJzdExvZ29Gb3JtYXR0ZWROYW1lIjoiMTY1XzhlYWQ3NTE2LTE4OTUtNDk4Ny04YTlkLTc0NDczMjEzYzUwOS5qcGciLCJzdFNlY3VyaXR5S2V5IjoiMjdjR0h0dTQyZFZhSlc2NDA0NzliYlEyOXc0dnhaeGUiLCJmbGdJc0FjdGl2ZSI6dHJ1ZSwiZmxnSXNEZWxldGVkIjpmYWxzZX0sImlhdCI6MTY3ODAyNjEyMX0.tiECPig7T0pNCblzVqeYR57F2YasJcG_tuaWpi9b3sY", ApiKeyVehicle.HEADER, "Authorization", ",");
    }
    
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsServiceImpl();
//    }
}