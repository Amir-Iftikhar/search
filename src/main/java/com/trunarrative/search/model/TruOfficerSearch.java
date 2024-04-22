package com.trunarrative.search.model;

import com.trunarrative.search.entity.Officer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TruOfficerSearch {
    int total_results;
    List<Officer> items;
}
