package com.api.tenejob.controller;

import com.api.tenejob.exception.ResourceNotFoundException;
import com.api.tenejob.model.Shift;
import com.api.tenejob.repository.WorkerShiftRepository;
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
public class ShiftController {

    private static final Logger logger = LoggerFactory.getLogger(ShiftController.class);

    @Autowired
    WorkerShiftRepository workerShiftRepository;

    @GetMapping("/shifts")
    public List<Shift> getAllShift() {
        return workerShiftRepository.findAll();
    }

    @PostMapping("/shift")
    public Shift createShift(@Valid @RequestBody Shift shift) {
        Shift createShift = workerShiftRepository.save(shift);
        logger.debug("Shift: " + createShift.getId() + " Created");
        return createShift;
    }

    @GetMapping("/shift/{id}")
    public Shift getShiftById(@PathVariable(value = "id") Long shiftId) {
        return workerShiftRepository.findById(shiftId)
                .orElseThrow(() -> new ResourceNotFoundException("Shift", "id", shiftId));
    }

    @PutMapping(value = "/shift/{id}")
    public Shift updateShift(@PathVariable(value = "id") Long shiftId,
                                           @Valid @RequestBody Shift shiftDetails) {

        Shift shift = workerShiftRepository.findById(shiftId)
                .orElseThrow(() -> new ResourceNotFoundException("Shift", "id", shiftId));

        shift.setDay(shiftDetails.getDay());

        Shift updatedShift = workerShiftRepository.save(shift);
        logger.debug("Shift: " + updatedShift.getId() + " Updated");
        return updatedShift;
    }

    @DeleteMapping("/shift/{id}")
    public ResponseEntity<?> deleteWorker(@PathVariable(value = "id") Long shiftId) {
        Shift shift = workerShiftRepository.findById(shiftId)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", shiftId));

        workerShiftRepository.delete(shift);
        logger.debug("Shift: " + shiftId + " Deleted");
        return ResponseEntity.ok().build();
    }
}
