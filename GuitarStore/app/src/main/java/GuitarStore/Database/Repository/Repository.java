package GuitarStore.Database.Repository;

import java.util.List;

public interface Repository<T, K extends Comparable<? super K>> {
    void Add(T entity);

    void AddRange(List<T> entity);

    void Remove(T entity);

    T FindById(K key);

    <S extends Comparable<? super S>> T FindByKey(KeySelector<T, S> selector, S key);

    List<T> GetAll();
}
