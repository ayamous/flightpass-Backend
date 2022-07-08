//package ma.itroad.ram.flightPass.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.reactive.config.CorsRegistry;
//import org.springframework.web.reactive.config.EnableWebFlux;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//@Configuration
//@EnableWebFlux
//public class CorsConfig implements WebFluxConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry corsRegistry) {
//        corsRegistry.addMapping("/**")
//                        .allowedMethods(HttpMethod.GET.toString(), HttpMethod.POST.toString(),
//                                HttpMethod.PUT.toString(), HttpMethod.DELETE.toString(), HttpMethod.OPTIONS.toString())
//                        .allowedOrigins("*");
//    }
//
//}
