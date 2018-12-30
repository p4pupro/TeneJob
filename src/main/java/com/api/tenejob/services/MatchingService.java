package com.api.tenejob.services;

import com.api.tenejob.model.Shift;
import com.api.tenejob.model.Worker;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.List;
import com.google.gson.reflect.TypeToken;

/**
 * Created by p4pupro on 20/12/2018.
 */

@Service
public class MatchingService  {


    public JsonObject getInputJson () {

        JsonObject jsonObject = null;

        try {

            //File jsonFile = Paths.get("C:\\Users\\papue\\Desktop\\TeneJob\\examples\\example_1.json").toFile();
            //File jsonFile = Paths.get("C:\\Users\\papue\\Desktop\\TeneJob\\examples\\example_2.json").toFile();
            //File jsonFile = Paths.get("example_1.json").toFile();
            //File jsonFile = Paths.get("example_2.json").toFile();

            Gson gson = new Gson();
            File jsonFile = Paths.get("C:\\Users\\papue\\Desktop\\TeneJob\\examples\\example_2.json").toFile();
            jsonObject = gson.fromJson(new FileReader(jsonFile), JsonObject.class);


        } catch (FileNotFoundException flnf) {
            flnf.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }

        return jsonObject;
    }

    public List<Worker> castingJsonToListWorker (JsonObject jsonObject) {
        Gson gson = new Gson();
        JsonArray objArray = jsonObject.getAsJsonArray("workers");
        Type type = new TypeToken<List<Worker>>(){}.getType();
        List<Worker> listWorker = gson.fromJson(objArray, type );

        return listWorker;
    }

    public List<Shift> castingJsonToListShift (JsonObject jsonObject) {
        Gson gson = new Gson();
        JsonArray objArray = jsonObject.getAsJsonArray("shifts");
        Type type = new TypeToken<List<Shift>>(){}.getType();
        List<Shift> listShift = gson.fromJson(objArray, type );

        return listShift;
    }
}
