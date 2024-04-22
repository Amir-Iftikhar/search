package com.trunarrative.search.service;

import com.trunarrative.search.entity.Company;
import com.trunarrative.search.entity.Officer;
import com.trunarrative.search.model.CompanySearch;
import com.trunarrative.search.model.TruOfficerSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TruSearchService {

    private final WebClient webClient;


    @Value("${trunarrative.api.company.search.uri}")
    private String companySearchUri;
    @Value("${trunarrative.api.officer.search.uri}")
    private String officerSearchUri;
    @Value("${trunarrative.api.call.timeout}")
    private long API_CALL_TIME_OUT;


    @Autowired
    public TruSearchService(WebClient webClient) {
        this.webClient = webClient;
    }

    public CompanySearch getCompanyDataFromTruApi(String search_term) {
        CompanySearch response = null;
        try {
            response = webClient.get()
                    .uri(companySearchUri, search_term)
                    .retrieve()
                    .bodyToFlux(CompanySearch.class)
                    .timeout(Duration.ofMillis(API_CALL_TIME_OUT))
                    .blockLast();
        } catch (Exception e) {
            // Do your specific error handling
            System.out.println(e);
        }
        if (response != null && response.getItems() != null)
            response.getItems().forEach(c -> c.setOfficers(getCompanyOfficersFromTruApi(c)));
        return response;
    }


    private List<Officer> getCompanyOfficersFromTruApi(Company company) {
        System.out.println("get officers for Company :" + company.getCompany_number());
        TruOfficerSearch response = null;
        try {
            response = webClient.get()
                    .uri(officerSearchUri, company.getCompany_number())
                    .retrieve()
                    .bodyToFlux(TruOfficerSearch.class)
                    .timeout(Duration.ofMillis(API_CALL_TIME_OUT))
                    .blockLast();
        } catch (Exception e) {
            // Do your specific error handling
            System.out.println(e);
        }
        //if no officers found return empty list
        if (response == null || response.getItems() == null)
            return Collections.emptyList();
        //remove any inactive officers
        response.setItems(response.getItems().stream()
                .filter(o -> o.getResigned_on() == null)
                .collect(Collectors.toList()));
        //update company for all officers
        response.getItems().forEach(o -> o.setCompany(company));
        return response.getItems();
    }


}
