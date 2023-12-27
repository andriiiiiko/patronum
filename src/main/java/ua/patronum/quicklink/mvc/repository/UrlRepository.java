package ua.patronum.quicklink.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.patronum.quicklink.data.entity.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
}
