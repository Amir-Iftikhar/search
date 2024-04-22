package com.trunarrative.search.dto;

import com.trunarrative.search.entity.Address;
import lombok.Builder;
import lombok.Data;

@Data
public class AddressDTO {

    private String locality;
    private String postal_code;
    private String premises;
    private String address_line_1;
    private String country;

    public AddressDTO(Address address) {
        this.locality = address.getLocality();
        this.postal_code = address.getPostal_code();
        this.premises = address.getPremises();
        this.address_line_1 = address.getAddress_line_1();
        this.country = address.getCountry();
    }
}
