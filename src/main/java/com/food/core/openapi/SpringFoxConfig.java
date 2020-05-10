package com.food.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.classmate.TypeResolver;
import com.food.api.dto.CozinhaDto;
import com.food.api.dto.PedidoResumoDto;
import com.food.api.exceptionhandler.ApiError;
import com.food.api.openapi.model.CozinhasDtoOpenApi;
import com.food.api.openapi.model.PageableModelOpenApi;
import com.food.api.openapi.model.PedidosResumoDtoOpenApi;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer{

	
	@Bean
	public Docket apiDocket() {
		
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
					.select()
						.apis(RequestHandlerSelectors.basePackage("com.food.api"))
						.paths(PathSelectors.any())
						.build()
					.useDefaultResponseMessages(false)
					.globalResponseMessage(RequestMethod.GET, globalGetReponseMessages())
					.globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
			        .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
			        .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
			        .additionalModels(typeResolver.resolve(ApiError.class))
			        .ignoredParameterTypes(ServletWebRequest.class)
			        .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
			        .alternateTypeRules(AlternateTypeRules.newRule(
			        		typeResolver.resolve(Page.class, CozinhaDto.class), 
			        		CozinhasDtoOpenApi.class))
			        .alternateTypeRules(AlternateTypeRules.newRule(
			        		typeResolver.resolve(Page.class, PedidoResumoDto.class),
			        		PedidosResumoDtoOpenApi.class))
					.apiInfo(apiInfo())
					.tags(
							new Tag("Cidades", "Gerencia as cidades"),
							new Tag("Grupos", "Gerencia os grupos de usuários"),
							new Tag("Cozinhas", "Gerencia as cozinhas"),
							new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
							new Tag("Pedidos", "Gerencia os pedidos"),
							new Tag("Restaurantes", "Gerencia os restaurantes")
							);
	}
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
		
	}
	
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("FOOD-API")
				.description("API aberta para restaurantes")
				.version("1")
				.contact(new Contact("Food", "https://github.com/lelis685", "lelis685@gmail.com"))
				.build();
	}
	
	
	private List<ResponseMessage> globalGetReponseMessages(){
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.responseModel(new ModelRef("Problema"))
					.message("Erro interno do servidor")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_FOUND.value())
					.responseModel(new ModelRef("Problema"))
					.message("Recurso não encontrado")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build()
				);
					
	}
	
	
	private List<ResponseMessage> globalPostPutResponseMessages() {
	   
		return Arrays.asList(
	            new ResponseMessageBuilder()
	                .code(HttpStatus.BAD_REQUEST.value())
	                .message("Requisição inválida (erro do cliente)")
	                .responseModel(new ModelRef("Problema"))
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
	                .responseModel(new ModelRef("Problema"))
	                .message("Erro interno no servidor")
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.NOT_ACCEPTABLE.value())
	                .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
	                .responseModel(new ModelRef("Problema"))
	                .message("Requisição recusada porque o corpo está em um formato não suportado")
	                .build()
	        );
	}

	
	private List<ResponseMessage> globalDeleteResponseMessages() {
	    return Arrays.asList(
	            new ResponseMessageBuilder()
	                .code(HttpStatus.BAD_REQUEST.value())
	                .responseModel(new ModelRef("Problema"))
	                .message("Requisição inválida (erro do cliente)")
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
	                .responseModel(new ModelRef("Problema"))
	                .message("Erro interno no servidor")
	                .build()
	        );
	}
	
	
}
