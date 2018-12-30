package com.api.tenejob.controller;


import com.api.tenejob.model.Matching;
import com.api.tenejob.model.Shift;
import com.api.tenejob.model.Worker;
import com.api.tenejob.services.MatchingService;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}