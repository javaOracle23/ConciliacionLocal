package com.conciliacion.parquet.utilities;

import org.apache.parquet.io.OutputFile;
import org.apache.parquet.io.PositionOutputStream;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class LocalOutputFile implements OutputFile {
    public class LocalPositionOutputStream extends PositionOutputStream {
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
