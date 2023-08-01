package com.aukcje.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "offer_details")
@Setter
@Getter
@NoArgsConstructor
public class OfferDetails {

    //TODO: zmienić na private

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    Long id;

    @Column(name="TITLE")
    String title;

    @Column(name="DESCRIPTION")
    String description;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ID_ITEM_CONDITION")
    ItemCondition itemCondition;
}
