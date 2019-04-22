package dst.courseproject.repositories;

import dst.courseproject.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByNameAndDeletedOnNull(String name);

    List<Category> findAllByDeletedOnNull();

    Category findByIdAndDeletedOnNull(String id);
}
