package com.conciliacion.parquet.services.implementation;


import com.conciliacion.parquet.services.interfaces.IRepositorioAzure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class RepositorioAzureServiceImpl implements IRepositorioAzure {

    private static Logger LOGGER = LoggerFactory.getLogger(RepositorioAzureServiceImpl.class);

    @Override
    public Object guardarArchivo(File outputFile) {
//        Map<String, Object> mapResponse = new HashMap<String, Object>();
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(outputFile);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        String blobName = outputFile.getName();
//        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(ConstantesRepositorioAzure.NOMBRE_CUENTA, ConstantesRepositorioAzure.KEY);
//        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
//                .endpoint("https://" + ConstantesRepositorioAzure.NOMBRE_CUENTA + ".blob.core.windows.net/")
//                .credential(credential)
//                .buildClient();
//
//        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(ConstantesRepositorioAzure.NOMBRE_CONTENEDOR);
//
//        if (!containerClient.exists()) {
//            containerClient.create();
//        }
//        BlobClient blobClient = containerClient.getBlobClient(blobName);
//        try {
//            blobClient.upload(inputStream,true);
//            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesRepositorioAzure.MENSAJE_OK_REPOSITORIO );
//            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
//        } catch (Exception e) {
//            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesRepositorioAzure.MENSAJE_ERROR_REPOSITORIO );
//            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_CAMPO_OBLIGATORIO );
//            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
//            return mapResponse;
//        }
//        return mapResponse;
        return null;
    }




}
