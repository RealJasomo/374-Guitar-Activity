package GuitarStore.Database;

import java.util.List;

public interface Database<T> {
    void SaveChanges();

    void LoadDatabase();

    List<T> Set();
}
