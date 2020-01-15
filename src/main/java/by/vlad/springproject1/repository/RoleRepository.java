package by.vlad.springproject1.repository;

import by.vlad.springproject1.entity.enums.Project;
import by.vlad.springproject1.entity.enums.Role;
import by.vlad.springproject1.entity.enums.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByUserAndProject(User user, Project project);
}
