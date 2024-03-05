package org.chainoptim.shared.search.model;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PaginatedResults<T> {
    public List<T> results;
    public long totalCount;
}
