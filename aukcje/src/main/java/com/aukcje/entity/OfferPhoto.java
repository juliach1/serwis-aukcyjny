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

    //TODO: zmienić na private

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ID_OFFER")
    Offer offer;

    @Column(name="PATH")
    String path;

    @Column(name="SEQUENCE")
    Integer sequence;
}
