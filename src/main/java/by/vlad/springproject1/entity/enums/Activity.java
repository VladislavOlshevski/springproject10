package by.vlad.springproject1.entity.enums;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;
    @Enumerated
    @Column(name = "status")private TaskStatus status;
    @Column(name = "elapsed")
    private Double elapsed;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    @Column(name = "description")
    private String description;
    public Activity() {
    }
}