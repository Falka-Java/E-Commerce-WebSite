package DAL;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface DAL<T> {
    Optional<T> get(long id);

    List<T> getAll();

    boolean add(T t);

    boolean update(int id, String[] params);

    boolean delete(T t);
    List<T> search(Predicate<T> filter);
}
