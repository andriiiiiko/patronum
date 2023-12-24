package ua.patronum.quicklink.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.patronum.quicklink.data.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
