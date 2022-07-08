//package ma.itroad.ram.flightpass.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfiguration  {
//
//
//import org.springframework.context.annotation.Bean;
//
//@Bean
//    public WebMvcConfigurer filter() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedMethods(HttpMethod.GET.toString(), HttpMethod.POST.toString(),
//                                HttpMethod.PUT.toString(), HttpMethod.DELETE.toString(), HttpMethod.OPTIONS.toString())
//                        .allowedOrigins("*");
//            }
//        };
//    }
//}
