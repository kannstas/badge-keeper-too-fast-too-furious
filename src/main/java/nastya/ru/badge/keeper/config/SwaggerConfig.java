package nastya.ru.badge.keeper.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server().url("http://app:8080")
                        )
                )
                .info(new Info()
                        .title("badge keeper API")
                        .description("Задачи приложения: " +
                                     "1.добавление и изменение данных о работниках и их персональных данных;\n" +
                                     "2.выдача бейджиков для работников.")
                        .contact(new Contact()
                                        .name("Kulakova Anastasia")
                                        .email("k.annstas@gmail.com")
                                        .url("https://github.com/kannstas"))
                );
    }
}