package by.vlad.springproject1.entity.enums;

import javax.persistence.*;

@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "token")
    private String token;
    public Token() {
    }
}