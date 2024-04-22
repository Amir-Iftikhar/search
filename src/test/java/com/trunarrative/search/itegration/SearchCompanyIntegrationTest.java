package com.trunarrative.search.itegration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trunarrative.search.entity.Company;
import com.trunarrative.search.repo.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SearchCompanyIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void confirmCompanySearchIsAddedToDb() throws Exception {

        mockMvc.perform(post("/TruProxyAPI/Company/Search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"companyName\" : \"BBC LIMITED\", \"companyNumber\" : \"06500244\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Company> company = companyRepository.findById("06500244");
        assertThat(company.get().getTitle()).isEqualTo("BBC LIMITED");
        assertThat(company.get().getCompany_number()).isEqualTo("06500244");
        assertThat(company.get().getCompany_type()).isEqualTo("ltd");
        assertThat(company.get().getCompany_status()).isEqualTo("active");
        assertThat(company.get().getDate_of_creation()).isEqualTo("2008-02-11");
        assertThat(company.get().getAddress().getPremises()).isEqualTo("Boswell Cottage Main Street");
        assertThat(company.get().getAddress().getAddress_line_1()).isEqualTo("North Leverton");
        assertThat(company.get().getAddress().getLocality()).isEqualTo("Retford");
        assertThat(company.get().getAddress().getPostal_code()).isEqualTo("DN22 0AD");
        assertThat(company.get().getAddress().getCountry()).isEqualTo("England");

    }


    @Test
    void findCompanyApiReturnsHttpStatusOkAndFiltersNonActiveCompany() throws Exception {

        mockMvc.perform(post("/TruProxyAPI/Company/Search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("activeCompaniesOnly", "true")
                        .content("{ \"companyName\" : \"TRUNARRATIVE LTD\", \"companyNumber\" : \"10241297\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        //confirm company found and added to Db but not returned as json
        Optional<Company> company = companyRepository.findById("10241297");
        assertThat(company.get().getTitle()).isEqualTo("TRUNARRATIVE LTD");
        assertThat(company.get().getCompany_number()).isEqualTo("10241297");
        assertThat(company.get().getCompany_status()).isEqualTo("dissolved");

    }

}
