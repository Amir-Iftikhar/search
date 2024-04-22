package com.trunarrative.search.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Companies")
public class Company {
    @Id
    private String company_number;
    private String company_type;
    private String title;
    private String company_status;
    private String date_of_creation;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(mappedBy = "company")
    private List<Officer> officers;
}
