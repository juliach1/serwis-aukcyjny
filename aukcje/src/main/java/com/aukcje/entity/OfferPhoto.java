package com.aukcje.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "offer_photo")
@Setter
@Getter
@NoArgsConstructor
public class OfferPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ID_OFFER")
    private Offer offer;

    @Column(name="PATH")
    private String path;

    @Column(name="SEQUENCE")
    private Integer sequence;

}
