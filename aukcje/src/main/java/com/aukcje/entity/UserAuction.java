package com.aukcje.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="user_auction")
@Getter
@Setter
@NoArgsConstructor
public class UserAuction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ID_USER")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ID_OFFER")
    private Offer offer;

    @Column(name="VALUE")
    private Double value;

    @Column(name="INSERT_TIME")
    private LocalDateTime insertTime;
}
