package lz.dao;

public interface RowMapper<T> {
    public T mapRow(String record);
}