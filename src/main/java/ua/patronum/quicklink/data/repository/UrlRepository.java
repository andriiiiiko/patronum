package ua.patronum.quicklink.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.patronum.quicklink.data.entity.Url;
import ua.patronum.quicklink.data.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    List<Url> findByExpirationDateAfter(LocalDateTime expirationDate);
    List<Url> findByUser(User user);
}
