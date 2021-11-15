package chomiuk.jacek.ui;

import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan("chomiuk.jacek")
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class AppSpringConfig {
    private final Environment environment;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public Jdbi jdbi() {
        var url = environment.getRequiredProperty("jdbi.url");
        var username = environment.getRequiredProperty("jdbi.username");
        var password = environment.getRequiredProperty("jdbi.password");
        return Jdbi.create(url, username, password);
    }

}
