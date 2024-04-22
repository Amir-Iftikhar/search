package com.trunarrative.search.controller;

import com.trunarrative.search.dto.CompanySearchDTO;
import com.trunarrative.search.model.CompanySearch;
import com.trunarrative.search.model.SearchRequestBody;
import com.trunarrative.search.service.SearchService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/TruProxyAPI/Company")
public class SearchController {

    private final SearchService service;

    public SearchController(SearchService service) {
        this.service = service;
    }

    @GetMapping("/Search")
    public String searchForm(Model model) {
        model.addAttribute("search", new SearchRequestBody());
        return "search";
    }

    @PostMapping(path = "web/Search",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String findCompany(SearchRequestBody body, @RequestParam(defaultValue = "false") Boolean activeCompaniesOnly, Model model) {
        CompanySearch foundCompanies = service.findCompany(body.companyNumber, body.companyName, activeCompaniesOnly);
        model.addAttribute("result", new CompanySearchDTO(foundCompanies));
        return "result";
    }

    @PostMapping(path = "/Search",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CompanySearchDTO> findCompanyApi(@RequestBody SearchRequestBody body, @RequestParam(defaultValue = "false") Boolean activeCompaniesOnly) {
        CompanySearch foundCompanies = service.findCompany(body.companyNumber, body.companyName, activeCompaniesOnly);
        if (foundCompanies == null || foundCompanies.getTotal_results() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new CompanySearchDTO(foundCompanies));
        }
    }

}
