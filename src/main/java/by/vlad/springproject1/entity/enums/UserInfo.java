package by.vlad.springproject1.entity.enums;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "name")
    private String name;
    @Column(name = "bio")
    private String bio;
    @Column(name = "url")private String url;
    @Column(name = "company")
    private String company;
    @Column(name = "location")
    private String location;
    public UserInfo() {
    }
}