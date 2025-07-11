package com.conciliacion.parquet.utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class ConvertirResultSetAStream {


    public static <T> Stream<T> convert(ResultSet resultSet, RowMapper<T> rowMapper) throws SQLException {
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(
                new ResultSetIterator<>(resultSet, rowMapper),
                Spliterator.ORDERED);
        return StreamSupport.stream(spliterator, false);
    }

    public interface RowMapper<T> {
        T mapRow(ResultSet rs) throws SQLException;
    }

    static class ResultSetIterator<T> implements java.util.Iterator<T> {
        private final ResultSet resultSet;
        private final RowMapper<T> rowMapper;
        private boolean hasNext = true;

        public ResultSetIterator(ResultSet resultSet, RowMapper<T> rowMapper) throws SQLException {
            this.resultSet = resultSet;
            this.rowMapper = rowMapper;
            if (resultSet != null && !resultSet.next()) {
                hasNext = false;
            }
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public T next() {
            if (!hasNext) {
                throw new java.util.NoSuchElementException();
            }
            try {
                T result = rowMapper.mapRow(resultSet);
                hasNext = resultSet.next();
                return result;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}



