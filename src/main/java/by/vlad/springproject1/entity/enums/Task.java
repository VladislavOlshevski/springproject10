package by.vlad.springproject1.entity.enums;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Task parent;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @Enumerated
    @Column(name = "type")
    private TaskType type;
    @Column(name = "estimate")
    private Double estimate;
    @Transient
    private Double elapsed;
    @Column(name = "opened")
    private LocalDateTime opened;
    @Column(name = "due")
    private LocalDate due;
    @OneToOne(mappedBy = "task", cascade = {CascadeType.ALL}, orphanRemoval =
            true)
    private TaskInfo taskInfo;
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL}, orphanRemoval
            = true)
    private List<Task> subtasks;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval =
            true)
    private List<Activity> activities;
    @Transient
    private Activity lastActivity;
    public Task() {
    }
}
