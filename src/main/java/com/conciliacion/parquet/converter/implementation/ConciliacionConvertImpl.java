package com.conciliacion.parquet.converter.implementation;

import com.conciliacion.parquet.converter.interfaces.IConverter;
import com.conciliacion.parquet.dto.ConciliacionDto;
import com.conciliacion.parquet.entity.ConciliacionEntity;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ConciliacionConvertImpl implements IConverter<ConciliacionEntity, ConciliacionDto> {

    @Override
    public ConciliacionEntity fromDto(ConciliacionDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto,ConciliacionEntity.class);
    }

    @Override
    public ConciliacionDto fromEntity(ConciliacionEntity entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity,ConciliacionDto.class);
    }

    @Override
    public List<ConciliacionDto> fromListEntity(List<ConciliacionEntity> entity) {return List.of();}

    @Override
    public ConciliacionEntity fromDtoTercero(ConciliacionDto dto, ConciliacionEntity entity) {return null;
    }
}
