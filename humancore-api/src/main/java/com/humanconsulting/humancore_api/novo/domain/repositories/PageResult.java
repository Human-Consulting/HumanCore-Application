package com.humanconsulting.humancore_api.novo.domain.repositories;

import java.util.List;

public interface PageResult<T> {
    List<T> getContent();
    int getPageNumber();
    int getPageSize();
    long getTotalElements();
    int getTotalPages();
}
