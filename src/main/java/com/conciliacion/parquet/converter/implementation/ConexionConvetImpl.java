package com.conciliacion.parquet.converter.implementation;


import com.conciliacion.parquet.converter.interfaces.IConverter;
import com.conciliacion.parquet.dto.ConexionDto;
import com.conciliacion.parquet.entity.ConexionEntity;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ConexionConvetImpl implements IConverter<ConexionEntity,ConexionDto>{

    @Override
    public ConexionEntity fromDto(ConexionDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto,ConexionEntity.class);
    }

    @Override
    public ConexionDto fromEntity(ConexionEntity entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity,ConexionDto.class);
    }

    @Override
    public List<ConexionDto> fromListEntity(List<ConexionEntity> entity) {
        return List.of();
    }

    @Override
    public ConexionEntity fromDtoTercero(ConexionDto dto, ConexionEntity entity) {
        return null;
    }
}
