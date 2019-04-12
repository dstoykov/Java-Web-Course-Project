package dst.courseproject.repositories;

import dst.courseproject.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {
    Set<Log> getAllByIdNotNullOrderByDateDesc();
}
