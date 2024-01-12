package com.aukcje.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="USERNAME")
    private String username;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="FIRSTNAME")
    private String firstName;

    @Column(name="LASTNAME")
    private String lastName;

    @Column(name="EMAIL")
    private String email;

    @Column(name="AVERAGE_RATE")
    private Double averageRate;

    @Column(name="AVATAR_PATH")
    private String avatarPath;

    @ManyToOne(cascade = {CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="ID_USER_STATUS")
    private UserStatus userStatus;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "ID_USER"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROLE"))
    private Collection<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Address> addresses;

}
