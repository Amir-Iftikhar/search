package com.trunarrative.search.dto;

import com.trunarrative.search.entity.Officer;
import lombok.Builder;
import lombok.Data;

@Data
public class OfficerDTO {
    private String name;
    private String officer_role;
    private String appointed_on;
    private AddressDTO address;

    public OfficerDTO(Officer officer) {
        this.name = officer.getName();
        this.officer_role = officer.getOfficer_role();
        this.appointed_on = officer.getAppointed_on();
        this.address = new AddressDTO(officer.getAddress());
    }

}
