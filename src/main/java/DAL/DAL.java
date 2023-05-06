package DAL;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface DAL<T> {
    Optional<T> get(long id);

    List<T> getAll();

    boolean add(T t);

    //Todo: change String[] params to T t
    boolean update(int id, String[] params);


    //Todo: change to int id
    boolean delete(T t);
    List<T> search(Predicate<T> filter);
}
