package dst.courseproject.repositories;

import dst.courseproject.entities.User;
import dst.courseproject.services.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Long countUsersByEmailIsNotNull();

    Long countUsersByDeletedOnIsNull();

    User findByEmailAndDeletedOnIsNull(String email);

    User findByIdEqualsAndDeletedOnIsNull(String id);

    User findByIdEqualsAndDeletedOnNotNull(String id);

    User findByIdAndDeletedOnNotNull(String id);

    List<User> getAllByEmailIsNotOrderByDeletedOn(String email);

    User findByIdEquals(String id);

    User findByEmailEquals(String email);

    @Override
    @Query("SELECT u FROM User u WHERE u.deletedOn IS NULL")
    List<User> findAll();

    @Override
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.deletedOn IS NULL")
    User getOne(@Param("id") String id);
}
