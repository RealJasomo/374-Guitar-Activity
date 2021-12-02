package GuitarStore.Database;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import GuitarStore.Database.Annotations.Entity;

public class JsonDatabase<T> implements Database<T> {

    private List<T> Entities;

    private Class<T> EntityType;

    public JsonDatabase(Class<T> type) {
        Entities = new ArrayList<T>();
        EntityType = type;
    }

    @Override
    public void SaveChanges() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            String filename = GetEntityFileName();
            if (filename != null) {
                File dir = new File("../database/entities");
                dir.mkdirs();
                File file = new File(dir, filename);
                FileWriter writer = new FileWriter(file);
                gson.toJson(Entities, writer);
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void LoadDatabase() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            String filename = GetEntityFileName();
            if (filename != null) {
                Reader reader = new BufferedReader(new FileReader("../database/entities/" + filename));

                T[] entities = (T[]) gson.fromJson(reader, EntityType.arrayType());
                if (entities != null) {
                    Entities = new ArrayList<T>();
                    Entities.addAll(Arrays.asList(entities));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> Set() {
        return Entities;
    }

    private String GetEntityFileName() throws Exception {
        if (!EntityType.isAnnotationPresent(Entity.class)) {
            System.out.println(EntityType.getName());
            throw new Exception("The provided type " + EntityType.getSimpleName() + " is not an entity");
        }
        Entity info = EntityType.getAnnotation(Entity.class);
        if (info.FileName() != null) {
            return info.FileName();
        }
        return null;
    }

}
