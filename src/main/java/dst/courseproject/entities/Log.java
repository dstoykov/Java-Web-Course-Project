package dst.courseproject.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    public Log() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}