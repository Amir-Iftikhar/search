package com.trunarrative.search.dto;


import com.trunarrative.search.entity.Company;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyDTO {

    private String company_number;
    private String company_type;
    private String title;
    private String company_status;
    private String date_of_creation;
    private AddressDTO address;
    private List<OfficerDTO> officers;

    public CompanyDTO(Company company) {
        this.company_number = company.getCompany_number();
        this.company_type = company.getCompany_type();
        this.title = company.getTitle();
        this.company_status = company.getCompany_status();
        this.date_of_creation = company.getDate_of_creation();
        this.address = new AddressDTO(company.getAddress());
        this.officers = new ArrayList<OfficerDTO>();
        if (company.getOfficers() != null && !company.getOfficers().isEmpty())
            company.getOfficers().forEach(o -> this.officers.add(new OfficerDTO(o)));
    }
}
