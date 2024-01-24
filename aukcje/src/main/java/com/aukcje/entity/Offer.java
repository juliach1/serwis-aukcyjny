package com.aukcje.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "offer")
@Setter
@Getter
@NoArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ID_OFFER_TYPE")
    private OfferType offerType;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ID_OFFER_STATUS")
    private OfferStatus offerStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "ID_OFFER_DETAILS")
    private OfferDetails offerDetails;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ID_USER")
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ID_CATEGORY")
    private Category category;

    @Column(name="PRICE")
    private Double price;

    @Column(name="VIEWS")
    private Long views;

    @Column(name="QUANTITY")
    private Integer quantity;

    @Column(name="INSERT_DATE")
    private LocalDateTime insertDate;

    @Column(name="END_DATE")
    private LocalDateTime endDate;

}
