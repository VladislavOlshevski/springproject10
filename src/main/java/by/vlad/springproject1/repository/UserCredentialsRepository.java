package by.vlad.springproject1.repository;

import by.vlad.springproject1.entity.enums.UserCredentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends
        CrudRepository<UserCredentials, Long> {
    Optional<UserCredentials> findByLogin(String login);
}
