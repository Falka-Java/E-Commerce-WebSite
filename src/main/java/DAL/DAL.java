package DAL;

import java.util.List;
import java.util.Optional;

public interface DAL<T> {
    Optional<T> get(long id);

    List<T> getAll();

    boolean add(T t);

    boolean update(T t, String[] params);

    boolean delete(T t);
}
