package nastya.ru.badge.keeper.kafka;

import nastya.ru.badge.keeper.service.BadgeService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Logger;

@Service
public class KafkaConsumer {

    private BadgeService badgeService;
    Logger logger = Logger.getLogger(KafkaConsumer.class.getName());
    public KafkaConsumer(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @KafkaListener(topics = "employee_go_out_more_than_7_times_in_5_minutes", groupId = "employee_go_out")
    public void disableBadge(ConsumerRecord<String, String> record) {
        logger.info("reading from a topic %s : start".formatted("employee_go_out_more_than_7_times_in_5_minutes"));
        logger.info("disable badge: start: id = %s".formatted(UUID.fromString(record.key())));

        badgeService.disable(UUID.fromString(record.key()));

        logger.info("disable badge: finish: id = %s".formatted(UUID.fromString(record.key())));
    }
}