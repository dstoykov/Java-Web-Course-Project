package dst.courseproject.repositories;

import dst.courseproject.entities.Category;
import dst.courseproject.entities.User;
import dst.courseproject.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    Video getByVideoIdentifierEqualsAndDeletedOnNull(String videoIdentifier);

    Set<Video> getAllByDeletedOnNullOrderByUploadedOnDesc();

    Set<Video> getAllByDeletedOnNullOrderByViewsDesc();

    Long countAllByIdIsNotNullAndDeletedOnNull();

    List<Video> getAllByAuthorAndDeletedOnNullOrderByUploadedOnDesc(User author);

    List<Video> getAllByCategoryAndDeletedOnNullOrderByUploadedOnDesc(Category category);

    List<Video> getAllByCategoryAndDeletedOnNullOrderByViewsDesc(Category category);
}
