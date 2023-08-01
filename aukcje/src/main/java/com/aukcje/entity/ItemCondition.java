package com.aukcje.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item_condition")
@Setter
@Getter
@NoArgsConstructor
public class ItemCondition {

    //TODO: zmienić na private

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    Integer id;

    @Column(name = "NAME")
    String name;

    @Column(name="DESCRIPTION")
    String description;
}
