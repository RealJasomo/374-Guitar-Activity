package GuitarStore.Database.Repository;

public interface KeySelector<T, K extends Comparable<? super K>> {
    K select(T entity);
}
