package dst.courseproject.repositories;

import dst.courseproject.entities.User;
import dst.courseproject.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    Video getByVideoIdentifierEquals(String videoIdentifier);

    Set<Video> getAllByIdNotNull();

    Long countAllByIdIsNotNull();

    List<Video> getAllByAuthorOrderByViewsDesc(User author);
}
