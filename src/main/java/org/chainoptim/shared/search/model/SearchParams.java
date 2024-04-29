package org.chainoptim.shared.search.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParams {
    private String searchQuery;
    private String filtersJson;
    private Map<String, String > filters;
    private String sortBy;
    private boolean ascending;
    private int page;
    private int itemsPerPage;

}
