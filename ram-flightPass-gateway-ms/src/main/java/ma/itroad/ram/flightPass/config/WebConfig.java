package ma.itroad.ram.flightPass.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

	
	@Bean
	RouterFunction<ServerResponse> routerFunction(GatewayHandler gatewayHandler) {
		return route(GET("/api/user"), gatewayHandler::getCurrentUser)
				.andRoute(GET("/private"), gatewayHandler::getPrivate);
	}
	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry.addMapping("/**")
				.allowedMethods(HttpMethod.GET.toString(), HttpMethod.POST.toString(),
						HttpMethod.PUT.toString(), HttpMethod.DELETE.toString(), HttpMethod.OPTIONS.toString())
				.allowedOrigins("*");
	}

	
	
	
}

