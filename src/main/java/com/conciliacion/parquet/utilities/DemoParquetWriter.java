package com.conciliacion.parquet.utilities;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Type;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.io.OutputFile;
import org.apache.parquet.io.PositionOutputStream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class DemoParquetWriter {
    public static void main(final String[] args) throws Exception {

        File outputFiles = new File("demo.parquet");

        if (outputFiles.exists()) {
            outputFiles.delete(); // true for recursive delete
        }

        final Schema schema = buildSchema();
        final LocalOutputFile outputFile = new LocalOutputFile(Path.of("demo.parquet"));
        try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(outputFile)
                .withCompressionCodec(CompressionCodecName.GZIP)
                .withSchema(schema)
                .withPageSize(1 << 20)
                .build())
        {
            writer.write(makeRecord(schema, "Foo", 10));
            writer.write(makeRecord(schema, "Bar", 27));
            writer.write(makeRecord(schema, "Car", 57));
        }
    }

    private static Schema buildSchema() {
        final Schema schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);
        schema.setFields(List.of(
                new Schema.Field("name", Schema.create(Type.STRING), null, null),
                new Schema.Field("age", Schema.create(Type.INT), null, null)
        ));
        return schema;
    }

    public static GenericData.Record makeRecord(final Schema schema, final String name, final int age) {
        final GenericData.Record record = new GenericData.Record(schema);
        record.put("name", name);
        record.put("age", age);
        return record;
    }

    // TODO: should be available with parquet 1.13.2
    private static class LocalOutputFile implements OutputFile {
        private class LocalPositionOutputStream extends PositionOutputStream {
            private final BufferedOutputStream stream;
            private long pos = 0;

            public LocalPositionOutputStream(final int buffer, final StandardOpenOption... openOption) throws IOException {
                stream = new BufferedOutputStream(Files.newOutputStream(path, openOption), buffer);
            }

            @Override
            public long getPos() {
                return pos;
            }

            @Override
            public void write(final int data) throws IOException {
                pos++;
                stream.write(data);
            }

            @Override
            public void write(final byte[] data) throws IOException {
                pos += data.length;
                stream.write(data);
            }

            @Override
            public void write(final byte[] data, final int off, final int len) throws IOException {
                pos += len;
                stream.write(data, off, len);
            }

            @Override
            public void flush() throws IOException {
                stream.flush();
            }

            @Override
            public void close() throws IOException {
                stream.close();
            }
        }

        private final Path path;

        public LocalOutputFile(final Path file) {
            path = file;
        }

        @Override
        public PositionOutputStream create(final long buffer) throws IOException {
            return new LocalPositionOutputStream((int) buffer, StandardOpenOption.CREATE_NEW);
        }

        @Override
        public PositionOutputStream createOrOverwrite(final long buffer) throws IOException {
            return new LocalPositionOutputStream((int) buffer, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        }

        @Override
        public boolean supportsBlockSize() {
            return true;
        }

        @Override
        public long defaultBlockSize() {
            return 512;
        }

        @Override
        public String getPath() {
            return path.toString();
        }
    }
}