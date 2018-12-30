package com.api.tenejob.controller;

import com.api.tenejob.exception.ResourceNotFoundException;
import com.api.tenejob.model.Worker;
import com.api.tenejob.repository.WorkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by p4pupro on 20/12/2018.
 */
@RestController
@RequestMapping("/api")
public class WorkerController {

    private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    WorkerRepository workerRepository;

    @GetMapping("/workers")
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    @PostMapping("/worker")
    public Worker createWorker(@Valid @RequestBody Worker worker) {
        Worker createWorker = workerRepository.save(worker);
        logger.debug("Worker: " + createWorker.getId() + " Created");
        return createWorker;
    }

    @GetMapping("/worker/{id}")
    public Worker getWorkerById(@PathVariable(value = "id") Long workerId) {
        return workerRepository.findById(workerId)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", workerId));
    }

    @PutMapping(value = "/worker/{id}")
    public Worker updateWorker(@PathVariable(value = "id") Long workerId,
                                           @Valid @RequestBody Worker workerDetails) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", workerId));

        worker.setPayrate(workerDetails.getPayrate());
        worker.setAvailability(workerDetails.getAvailability());

        Worker updatedWorker = workerRepository.save(worker);
        logger.debug("Worker: " + updatedWorker.getId() + " Updated");
        return updatedWorker;
    }

    @DeleteMapping("/worker/{id}")
    public ResponseEntity<?> deleteWorker(@PathVariable(value = "id") Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", workerId));

        workerRepository.delete(worker);
        logger.debug("Worker: " + workerId + " Deleted");
        return ResponseEntity.ok().build();
    }
}
