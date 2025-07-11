package com.conciliacion.parquet.services.interfaces;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface IRepositorioAzure {

    public Object guardarArchivo(File outputFile);

}
