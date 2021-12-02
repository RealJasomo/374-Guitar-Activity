package GuitarStore.Database.Entities;

import GuitarStore.Database.Annotations.Entity;
import GuitarStore.Database.Annotations.Key;

@Entity(FileName = "Test.json")
public class Test {
    @Key
    public Integer id;

    public String name;

    public Test(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
