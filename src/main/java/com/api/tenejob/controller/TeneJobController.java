package com.api.tenejob.controller;


import com.api.tenejob.model.Matching;
import com.api.tenejob.model.Shift;
import com.api.tenejob.model.Worker;
import com.api.tenejob.services.MatchingService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;


/**
 * Created by p4pupro on 20/12/2018.
 */

@RestController
@RequestMapping("/api")
public class TeneJobController {

    private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);


    @Autowired
    MatchingService matchingService;

    @GetMapping
    public String Hello () {
        return " Welcome to the TeneJob API. You can try request to /matching endpoint.";
    }


    /**
    * @Author: Domingo Pérez
      @param  jsonData
    * The algorithm logic is as follows:
    * Having a list of shifts, you go through and search within the list of availability of workers, when they "match",
    * you count the number of coincidences, this is key, because we will only take those coincidences that your counter is one,
    * and the following add this a matching list, and remove the worker from the list of workers, as this has been paired.
    * All this process will be repeating while the size of the matching list and the list of shifts is not the same,
    * because when it has the same size it means that all shifts have been assigned.
    *
     */
    @PostMapping("/matching")
    public List<Matching> matching (@Valid @RequestBody String jsonData) {

        Gson gson = new Gson();
        JsonObject  jsonObject = gson.fromJson(jsonData, JsonObject.class);
        List<Worker> listWorker = matchingService.castingJsonToListWorker(jsonObject);
        List<Shift> listShift = matchingService.castingJsonToListShift(jsonObject);

        List<Matching> listMatching = new ArrayList<>();
        ArrayList<String> matchingDays = new ArrayList<>();

        do {

            if (matchingDays.size() == listShift.size()) { break; }

            for(int i = 0; i < listShift.size(); i ++) {
                String day = listShift.get(i).getDay()[0];
                if(!matchingDays.contains(day)) {
                    if((listWorker.stream()
                            .filter(worker -> Arrays.stream(worker.getAvailability()).anyMatch(day::equals))
                            .count()) == 1) {

                        // Looking worker and shift:
                        Optional<Worker> work = listWorker.stream().filter(worker -> Arrays.stream(worker.getAvailability()).anyMatch(day::equals)).findFirst();
                        Optional<Shift>  shif = listShift.stream().filter(shft -> Arrays.stream(shft.getDay()).anyMatch(day::equals)).findFirst();

                        // Set worker and shift in matching list:
                        Matching matching = new Matching();
                        matching.setId(Long.parseLong(String.valueOf(i)));
                        matching.setWorker(work.get());
                        matching.setShift(shif.get());
                        listMatching.add(matching);

                        // Add the day from the array of availability days:
                        matchingDays.add(day);

                        // Remove the worker that has been assigned to the optimal shift:
                        listWorker.remove(work.get());
                    } else if((listWorker.stream()
                            .filter(worker -> Arrays.stream(worker.getAvailability()).anyMatch(day::equals))
                            .count()) > 1) {
                        logger.debug("I'm sorry, but the algorithm doesn't make magic, only one worker can be assigned to a shift.");
                    }
                }
            }

        } while(true);

        return listMatching;
    }

    /**
     * @Author: Domingo Pérez
     * Similar to another, but this case without post request
     * json is given to path.
     *
     */
    /*
    @GetMapping("/matching")
    public List<Matching> matching () {

        JsonObject jsonObject = matchingService.getInputJson();
        List<Worker> listWorker = matchingService.castingJsonToListWorker(jsonObject);
        List<Shift> listShift = matchingService.castingJsonToListShift(jsonObject);

        List<Matching> listMatching = new ArrayList<>();
        ArrayList<String> matchingDays = new ArrayList<>();

        do {

            if (matchingDays.size() == listShift.size()) { break; }

            for(int i = 0; i < listShift.size(); i ++) {
                String day = listShift.get(i).getDay()[0];
                if(!matchingDays.contains(day)) {
                    if((listWorker.stream()
                            .filter(worker -> Arrays.stream(worker.getAvailability()).anyMatch(day::equals))
                            .count()) == 1) {

                                // Looking worker and shift:
                                Optional<Worker> work = listWorker.stream().filter(worker -> Arrays.stream(worker.getAvailability()).anyMatch(day::equals)).findFirst();
                                Optional<Shift>  shif = listShift.stream().filter(shft -> Arrays.stream(shft.getDay()).anyMatch(day::equals)).findFirst();

                                // Set worker and shift in matching list:
                                Matching matching = new Matching();
                                matching.setId(Long.parseLong(String.valueOf(i)));
                                matching.setWorker(work.get());
                                matching.setShift(shif.get());
                                listMatching.add(matching);

                                // Add the day from the array of availability days:
                                matchingDays.add(day);

                                // Remove the worker that has been assigned to the optimal shift:
                                listWorker.remove(work.get());
                            } else if((listWorker.stream()
                            .filter(worker -> Arrays.stream(worker.getAvailability()).anyMatch(day::equals))
                            .count()) > 1) {
                                logger.debug("I'm sorry, but the algorithm doesn't make magic, only one worker can be assigned to a shift.");
                            }
                }
            }

        } while(true);

        return listMatching;
    }
    */
}