package com.conciliacion.parquet.converter.implementation;

import com.conciliacion.parquet.converter.interfaces.IConverter;
import com.conciliacion.parquet.dto.ParametrizacionDto;
import com.conciliacion.parquet.entity.ParametrizacionEntity;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ParametrizacionConvertImpl implements IConverter<ParametrizacionEntity, ParametrizacionDto> {

    @Override
    public ParametrizacionEntity fromDto(ParametrizacionDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto,ParametrizacionEntity.class);
    }

    @Override
    public ParametrizacionDto fromEntity(ParametrizacionEntity entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity,ParametrizacionDto.class);
    }

    @Override
    public List<ParametrizacionDto> fromListEntity(List<ParametrizacionEntity> entity) {return List.of();}

    @Override
    public ParametrizacionEntity fromDtoTercero(ParametrizacionDto dto, ParametrizacionEntity entity) {return null;
    }
}
