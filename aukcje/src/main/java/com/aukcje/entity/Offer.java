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
    Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ID_OFFER_TYPE")
    OfferType offerType;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ID_OFFER_STATUS")
    OfferStatus offerStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "ID_OFFER_DETAILS")
    OfferDetails offerDetails;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ID_USER")
    User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ID_CATEGORY")
    Category category;

    @Column(name="PRICE")
    Double price;

    @Column(name="VIEWS")
    Long views;

    @Column(name="QUANTITY")
    Integer quantity;

    @Column(name="INSERT_DATE")
    LocalDateTime insertDate;


    @Column(name="END_DATE")
    LocalDateTime endDate;


}
