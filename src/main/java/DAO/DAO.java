package DAO;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get(long id);

    List<T> getAll();

    boolean add(T t);

    boolean update(int id, String[] params);

    boolean delete(T t);

}
