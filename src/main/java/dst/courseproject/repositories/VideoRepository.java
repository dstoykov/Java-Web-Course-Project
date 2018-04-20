package dst.courseproject.repositories;

import dst.courseproject.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    Long countAllByIdIsNotNull();
}
