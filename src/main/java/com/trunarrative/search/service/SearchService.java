package com.trunarrative.search.service;

import com.trunarrative.search.entity.Company;
import com.trunarrative.search.model.CompanySearch;
import com.trunarrative.search.repo.CompanyRepository;
import com.trunarrative.search.repo.OfficerRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final TruSearchService truService;
    private final CompanyRepository companyRepository;
    private final OfficerRepository officerRepository;

    public SearchService(TruSearchService truService, CompanyRepository companyRepository, OfficerRepository officerRepository) {
        this.truService = truService;
        this.companyRepository = companyRepository;
        this.officerRepository = officerRepository;
    }

    public CompanySearch findCompany(String companyNumber, String companyName, Boolean activeCompaniesOnly) {
        String search_param;

        //if we have a company number , look in database first
        if (companyNumber != null && !companyNumber.isEmpty()) {
            search_param = companyNumber;
            CompanySearch searchResults1 = getSearchFromDb(companyNumber, activeCompaniesOnly);
            if (searchResults1 != null) {
                return searchResults1;
            }
        } else {
            search_param = companyName;
        }

        // not in database or search by name, get from tru api and save to db
        return getSearchFromApi(activeCompaniesOnly, search_param);
    }

    private CompanySearch getSearchFromApi(Boolean activeCompaniesOnly, String search_param) {
        CompanySearch truCompanySearch = truService.getCompanyDataFromTruApi(search_param);

        if (truCompanySearch != null && truCompanySearch.getTotal_results() > 0) {
            companyRepository.saveAll(truCompanySearch.getItems());
            truCompanySearch.getItems().forEach(c -> officerRepository.saveAll(c.getOfficers()));
            if (activeCompaniesOnly) {
                truCompanySearch.setItems(truCompanySearch.getItems().stream()
                        .filter(c -> c.getCompany_status().equalsIgnoreCase("active"))
                        .collect(Collectors.toList()));
                truCompanySearch.setTotal_results(truCompanySearch.getItems().size());
            }
        }
        return truCompanySearch;
    }

    private CompanySearch getSearchFromDb(String companyNumber, Boolean activeCompaniesOnly) {
        Optional<Company> optionalCompany = companyRepository.findById(companyNumber);
        if (activeCompaniesOnly
                && optionalCompany.isPresent()
                && !optionalCompany.get().getCompany_status().equalsIgnoreCase("active")) {
            optionalCompany = Optional.empty();
        }
        if (optionalCompany.isPresent()) {
            //found in database
            CompanySearch searchResults = new CompanySearch();
            searchResults.setTotal_results(1);
            searchResults.setItems(List.of(optionalCompany.get()));
            return searchResults;
        }
        return null;
    }

}
