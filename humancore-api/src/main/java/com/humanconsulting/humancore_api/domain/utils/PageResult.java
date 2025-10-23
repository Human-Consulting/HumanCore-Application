package com.humanconsulting.humancore_api.domain.utils;

import java.util.List;

public interface PageResult<T> {
    List<T> getContent();
    int getPageNumber();
    int getPageSize();
    long getTotalElements();
    int getTotalPages();
}