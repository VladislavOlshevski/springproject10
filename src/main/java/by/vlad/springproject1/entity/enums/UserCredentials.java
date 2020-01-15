package by.vlad.springproject1.entity.enums;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user_credentials")
public class UserCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "login")
    private String login;
    @Column(name = "hash")
    private String hash;
    public UserCredentials() {
    }
    public UserCredentials(User user, String login, String hash) {
        this.user = user;
        this.login = login;
        this.hash = hash;
    }
}