package by.vlad.springproject1.entity.enums;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "joined")
    private LocalDate joined;
    @OneToOne(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval =
            true)
    private UserCredentials userCredentials;
    @OneToOne(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval =
            true)
    private UserInfo userInfo;
    @ManyToMany
    @JoinTable(
            name = "user_follower",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> followers;
    @ManyToMany
    @JoinTable(
            name = "user_follower",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private List<User> followed;
    public User() {
    }
}