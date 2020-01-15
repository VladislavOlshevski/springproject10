package by.vlad.springproject1.repository;

import by.vlad.springproject1.entity.enums.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}