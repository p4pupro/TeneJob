package com.api.tenejob.services;


import com.api.tenejob.model.Shift;
import com.api.tenejob.model.Worker;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(MatchingService.class);

    /**
     * Just use in case you want try request GET method.
     * @return
     */
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

    /**
     * Casting jsonObject to listWorker
     * @param jsonObject
     * @return
     */
    public List<Worker> castingJsonToListWorker (JsonObject jsonObject) {
        Gson gson = new Gson();
        JsonArray objArray = jsonObject.getAsJsonArray("workers");
        Type type = new TypeToken<List<Worker>>(){}.getType();
        List<Worker> listWorker = gson.fromJson(objArray, type );

        return listWorker;
    }

    /**
     * Casting jsonObject to listShift
     * @param jsonObject
     * @return
     */
    public List<Shift> castingJsonToListShift (JsonObject jsonObject) {
        Gson gson = new Gson();
        JsonArray objArray = jsonObject.getAsJsonArray("shifts");
        Type type = new TypeToken<List<Shift>>(){}.getType();
        List<Shift> listShift = gson.fromJson(objArray, type );

        return listShift;
    }


    /**
     * @Author Domingo Pérez
     * Removes from the list of workers those on the matching list and
     * leaves a log message of those workers who have not been matched
     * @param listWorkerOriginal
     * @param listWorkerMatched
     */
    public void checkWorkerWithoutShift (List<Worker> listWorkerOriginal, List<Worker> listWorkerMatched) {
        for (Worker matchingWorkers : listWorkerMatched) {
            listWorkerOriginal.remove(matchingWorkers);
        }
        listWorkerOriginal.forEach( worker -> {
                                        logger.info("This worker with id: " + worker.getId() + " - could not be assigned, only one worker can be assigned to a shift.");
                                        logger.info("Worker details: " + worker.toString());
                                    });
        logger.debug("I'm sorry, but the algorithm doesn't make magic.");
    }

}
