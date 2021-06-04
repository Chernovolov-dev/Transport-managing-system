package ua.nure.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.diploma.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUserName(String username);
}
