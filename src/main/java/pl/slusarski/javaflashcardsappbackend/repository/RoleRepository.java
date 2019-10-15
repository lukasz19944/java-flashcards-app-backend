package pl.slusarski.javaflashcardsappbackend.repository;

import org.springframework.data.repository.CrudRepository;
import pl.slusarski.javaflashcardsappbackend.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

}
