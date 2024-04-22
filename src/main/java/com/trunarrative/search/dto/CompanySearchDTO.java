package com.trunarrative.search.dto;

import com.trunarrative.search.model.CompanySearch;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanySearchDTO {
    int total_results;
    List<CompanyDTO> items;

    public CompanySearchDTO(CompanySearch companySearch) {
        this.total_results = companySearch.getTotal_results();
        this.items = new ArrayList<CompanyDTO>();
        if (companySearch.getItems() != null) {
            companySearch.getItems().forEach(c -> this.items.add(new CompanyDTO(c)));
        }

    }

}
