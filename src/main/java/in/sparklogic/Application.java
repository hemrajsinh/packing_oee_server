package in.sparklogic;

import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import in.sparklogic.repository.CustomRepositoryImpl;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware", modifyOnCreate = false)
@EnableSwagger2
public class Application extends SpringBootServletInitializer {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Calcutta"));
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.US); // Set default Locale as US
		return slr;
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("i18n/messages"); // name of the resource bundle
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowCredentials(false)
						.allowedMethods("GET", "PUT", "POST", "OPTIONS", "DELETE").allowedOrigins("*");
			}
		};
	}
	
//	public void configure(WebSecurity  web) throws Exception {
//		web.ignoring().antMatchers("*/v2/api-docs", "*/configuration/ui", "*/swagger-resources/**", "*/configuration/**", "*/swagger-ui.html", "/webjars/**");
//	}
	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
//		authenticationMgr.
//			.withUser("jduser").password("jdu@123").authorities("ROLE_SA")
//			.and()
//			.withUser("jdadmin").password("jda@123").authorities("ROLE_USER","ROLE_ADMIN");
//	}
	
	public void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable();
		  http.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint()).accessDeniedPage("/");
		  http.authorizeRequests()
//		  	.antMatchers("/login").anonymous()
			.antMatchers("/logout").fullyAuthenticated()
			.antMatchers("*/oee").permitAll()
			.antMatchers("*/v2/api-docs", "*/configuration/ui", "*/swagger-resources/**", "*/configuration/**","*/swagger-ui.html", "/webjars/**").permitAll()
			.antMatchers("/user/**","/subject/**","/topic/**","/standard/**").hasRole("SuperAdmin")
			.antMatchers("/standard/**").hasRole("BOAdmin")
			.antMatchers("/topic/**","/subject/**").hasRole("BOTeacher") 
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").permitAll();
	}
	
	/*public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger/");

	}*/

	@Bean
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
	    return hemf.getSessionFactory();
	}
	
	@PostConstruct
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
	}
	
}
