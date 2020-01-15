package by.vlad.springproject1.entity.enums;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "public")
    private Boolean isPublic;
    @Column(name = "created")
    private LocalDateTime created;@OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval =
            true)
    private ProjectInfo projectInfo;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval
            = true)
    private List<Role> roles;
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;
    @Transient
    private Double estimate;
    @Transient
    private Double elapsed;
    public Project() {
    }
    public Project(User user, Boolean isPublic, LocalDateTime created) {
        this.user = user;
        this.isPublic = isPublic;
        this.created = created;
    }
    @PostLoad
    public void postLoad() {
        estimate = tasks
                .stream()
                .mapToDouble(t -> t.getEstimate() != null ? t.getEstimate() : 0)
                .sum();
        elapsed = tasks
                .stream()
                .mapToDouble(t -> t.getElapsed() != null ? t.getElapsed() : 0)
                .sum();
    } }
