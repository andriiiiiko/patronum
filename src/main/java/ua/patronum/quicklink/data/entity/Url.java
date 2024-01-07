package ua.patronum.quicklink.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(exclude = {"expirationDate", "visitCount", "user"})
@Builder
@Table(name = "urls")
@NoArgsConstructor
@AllArgsConstructor
public class Url {

    private static final int EXPIRATION_DAYS = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "short_url", nullable = false)
    private String shortUrl;

    @Builder.Default
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "visit_count", nullable = false)
    private int visitCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setExpirationDate() {
        this.expirationDate = this.dateCreated.plusDays(EXPIRATION_DAYS);
    }

    public void incrementVisitCount() {
        this.visitCount++;
    }
}
