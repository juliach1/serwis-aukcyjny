package com.aukcje.dto;

import lombok.Data;

@Data
public class AddressDTO {

    //TODO: zmienić na private
    public long id;
    public UserDTO user;
    public CountryDTO country;
    public String firstName;
    public String lastName;
    public String streetName;
    public String postalCode;
    public String phone;
    public String city;
    public Boolean isDeleted;

}
