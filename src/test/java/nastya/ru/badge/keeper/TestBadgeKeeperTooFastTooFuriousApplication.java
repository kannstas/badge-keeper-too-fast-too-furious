package nastya.ru.badge.keeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestBadgeKeeperTooFastTooFuriousApplication {

    @Bean
    @ServiceConnection
    KafkaContainer kafkaContainer() {
        return new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
    }


    public static void main(String[] args) {
        SpringApplication.from(BadgeKeeperTooFastTooFuriousApplication::main).with(TestBadgeKeeperTooFastTooFuriousApplication.class).run(args);
    }

}
