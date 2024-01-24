package com.aukcje.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private long id;
    private UserDTO user;
    private CountryDTO country;
    private String firstName;
    private String lastName;
    private String streetName;
    private String postalCode;
    private String phone;
    private String city;
    private Boolean isDeleted;

}
