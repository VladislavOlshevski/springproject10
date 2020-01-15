package by.vlad.springproject1.entity.enums;

import javax.persistence.*;

@Entity
@Table(name = "project_info")
public class ProjectInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "about")
    private String about;
    public ProjectInfo() {
    }
}