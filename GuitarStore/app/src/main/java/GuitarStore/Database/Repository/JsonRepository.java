package GuitarStore.Database.Repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import GuitarStore.Database.Database;
import GuitarStore.Database.JsonDatabase;
import GuitarStore.Database.Annotations.Entity;
import GuitarStore.Database.Annotations.Key;

public class JsonRepository<T, K extends Comparable<? super K>> implements Repository<T, K> {
    private Database<T> db;
    private Class<T> EntityType;

    public JsonRepository(Class<T> entityType) {
        db = new JsonDatabase<T>(entityType);
        db.LoadDatabase();
        EntityType = entityType;
    }

    public void Add(T entity) {
        db.Set().add(entity);
        db.SaveChanges();
    }

    public void AddRange(List<T> entities) {
        db.Set().addAll(entities);
        db.SaveChanges();
    }

    public void Remove(T entity) {
        db.Set().remove(entity);
        db.SaveChanges();
    }

    public T FindById(K id) {
        try {
            if (!EntityType.isAnnotationPresent(Entity.class)) {
                throw new Exception("The class " + EntityType.getSimpleName() + " is not an entity.");
            }
            for (Field field : EntityType.getDeclaredFields()) {
                if (field.isAnnotationPresent(Key.class)) {

                    return FindByKey((T element) -> {
                        try {
                            return (K) field.get(element);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }, id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <S extends Comparable<? super S>> T FindByKey(KeySelector<T, S> keySelector, S key) {
        Optional<T> found = db.Set()
                .stream()
                .filter((entity) -> keySelector.select(entity).compareTo(key) == 0)
                .findFirst();
        return found.isPresent() ? found.get() : null;
    }

    public List<T> GetAll() {
        return db.Set();
    }
}
