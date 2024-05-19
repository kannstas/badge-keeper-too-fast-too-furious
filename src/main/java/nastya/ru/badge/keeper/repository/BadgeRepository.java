package nastya.ru.badge.keeper.repository;

import nastya.ru.badge.keeper.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface BadgeRepository extends JpaRepository<Badge, UUID> {
    List<Badge> findBadgeByActiveIsTrue();
}
