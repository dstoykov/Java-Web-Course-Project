package dst.courseproject.repositories;

import dst.courseproject.entities.Comment;
import dst.courseproject.entities.Video;
import javafx.scene.effect.SepiaTone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    Set<Comment> getAllByVideoEqualsAndDeletedOnNullOrderByDateOfPublishingDesc(Video video);
}
