package com.aukcje.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AddressDTO {

    public long id;
    public UserDTO user;
    public CountryDTO country;
    public String firstName;
    public String lastName;
    public String streetName;
    public String postalCode;
    public String phone;
    public String city;

}
