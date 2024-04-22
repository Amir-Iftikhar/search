package com.trunarrative.search.controller;

import com.trunarrative.search.entity.Address;
import com.trunarrative.search.entity.Company;
import com.trunarrative.search.entity.Officer;
import com.trunarrative.search.model.CompanySearch;
import com.trunarrative.search.service.SearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = SearchController.class)
@WithMockUser
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    @Test
    void findCompanyApiReturnsHttpStatusOkAndSearchAsJson() throws Exception{
        when(searchService.findCompany(Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(expectedSearchResult());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/TruProxyAPI/Company/Search")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"companyName\" : \"BBC LIMITED\", \"companyNumber\" : \"06500244\" }")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(expectedJson, response.getContentAsString());

    }

    private String expectedJson = "{\"total_results\":1,\"items\":[{\"company_number\":\"06500244\",\"company_type\":null,\"title\":\"BBC LIMITED\",\"company_status\":\"active\",\"date_of_creation\":\"2008-02-11\",\"address\":{\"locality\":\"Retford\",\"postal_code\":\"DN22 0AD\",\"premises\":\"Boswell Cottage Main Street\",\"address_line_1\":\"North Leverton\",\"country\":\"England\"},\"officers\":[{\"name\":\"BOXALL, Sarah Victoria\",\"officer_role\":\"secretary\",\"appointed_on\":\"2008-02-11\",\"address\":{\"locality\":\"London\",\"postal_code\":\"SW20 0DP\",\"premises\":\"5\",\"address_line_1\":\"Cranford Close\",\"country\":\"England\"}}]}]}";

    private CompanySearch expectedSearchResult(){
        CompanySearch companySearch = new CompanySearch();
        companySearch.setItems(Arrays.asList(expectedCompany()));
        companySearch.setTotal_results(1);
        return companySearch;

    }

    private Company expectedCompany(){
        Company company= new Company();
        company.setCompany_number("06500244");
        company.setTitle("BBC LIMITED");
        company.setCompany_status("active");
        company.setDate_of_creation("2008-02-11");
        company.setAddress(expectedCompanyAddress());
        company.setOfficers(Arrays.asList(expectedOfficer()));
        return company;

    }

    private Officer expectedOfficer(){
        Officer officer = new Officer();
        officer.setName("BOXALL, Sarah Victoria");
        officer.setOfficer_role("secretary");
        officer.setAppointed_on("2008-02-11");
        officer.setAddress(expectedOfficerAddress());
        return officer;
    }

    private Address expectedCompanyAddress(){
        Address address = new Address();
        address.setAddress_line_1("North Leverton");
        address.setCountry("England");
        address.setLocality("Retford");
        address.setPremises("Boswell Cottage Main Street");
        address.setPostal_code("DN22 0AD");
        return address;
    }

    private Address expectedOfficerAddress(){
        Address address = new Address();
                address.setAddress_line_1("Cranford Close");
                address.setCountry("England");
                address.setLocality("London");
                address.setPremises("5");
                address.setPostal_code("SW20 0DP");
        return address;
    }

}