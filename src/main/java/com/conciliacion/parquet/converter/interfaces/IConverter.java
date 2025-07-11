package com.conciliacion.parquet.converter.interfaces;

import java.util.List;

public interface IConverter<Principal,convert>  {

    public Principal fromDto(convert dto);

    public convert fromEntity(Principal entity);

    public List<convert> fromListEntity(List<Principal> entity);

    public Principal fromDtoTercero(convert dto,Principal entity);

}
