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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name="DESCRIPTION")
    private String description;

}
