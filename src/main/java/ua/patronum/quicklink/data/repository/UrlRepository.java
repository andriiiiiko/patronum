package ua.patronum.quicklink.data.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.patronum.quicklink.data.entity.Url;
import ua.patronum.quicklink.data.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
@EnableCaching
public interface UrlRepository extends JpaRepository<Url, Long> {

    List<Url> findByUser(User user);

    @Cacheable("shortUrl")
    Optional<Url> findByShortUrl(String shortUrl);
}