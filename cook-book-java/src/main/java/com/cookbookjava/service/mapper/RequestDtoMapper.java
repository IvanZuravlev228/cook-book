package com.cookbookjava.service.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}