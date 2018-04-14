package dst.courseproject.repositories;

import dst.courseproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    User findByIdEquals(String id);

    List<User> getAllByEmailIsNot(String email);
}
