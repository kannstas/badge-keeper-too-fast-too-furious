package nastya.ru.badge.keeper.repository;

import nastya.ru.badge.keeper.entity.EmployeePersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface EmployeePersonalDataRepository extends JpaRepository<EmployeePersonalData, UUID> {
    Optional<EmployeePersonalData> findEmployeePersonalDataByEmployeeId(UUID employeeId);
}