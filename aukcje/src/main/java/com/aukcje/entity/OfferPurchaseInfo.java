package com.aukcje.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "offer_purchase_info")
@Setter
@Getter
@NoArgsConstructor
public class OfferPurchaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="ID_SELLER")
    private User seller;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="ID_BUYER")
    private User buyer;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="ID_BUYER")
    private Offer offer;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="ID_ADDRESS")
    private Address address;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="ID_PURCHASE_STATUS")
    private PurchaseStatus purchaseStatus;

    @Column(name="PURCHASE_TIME")
    private LocalDateTime purchaseTime;

    @Column(name="PRICE")
    private Double price;

    @Column(name="QUANTITY")
    private Integer quantity;

}
