package com.cookbookjava.service.mapper;

public interface RequestResponseDtoMapper<D, R, T> {
    T mapToModel(D dto);

    R mapToDto(T t);
}
