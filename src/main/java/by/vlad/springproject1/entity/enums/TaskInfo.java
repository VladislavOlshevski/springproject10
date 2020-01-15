package by.vlad.springproject1.entity.enums;

import javax.persistence.*;

@Entity
@Table(name = "task_info")
public class TaskInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    public TaskInfo() {
    }
}