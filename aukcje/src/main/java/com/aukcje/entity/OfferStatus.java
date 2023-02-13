package com.aukcje.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "offer_status")
@Setter
@Getter
@NoArgsConstructor
public class OfferStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    Integer id;

    @Column(name="NAME")
    String name;
}
