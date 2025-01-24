package tr.edu.ogu.ceng.notification.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    @Bean
    public RestClient getRestClientConfig() {

        return RestClient.create();
    }
}
