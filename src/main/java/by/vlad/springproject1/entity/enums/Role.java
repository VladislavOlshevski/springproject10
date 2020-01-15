package by.vlad.springproject1.entity.enums;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Project project;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated
    @Column(name = "role")
    private UserRole role;
    public Role() {
    }
}