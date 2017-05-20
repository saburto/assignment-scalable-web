package com.github.saburto.assignment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.saburto.assignment.comparator.Result;
import com.github.saburto.assignment.service.DiffService;

@RestController
public class DiffController {
    
    @Autowired
    private DiffService diffService;

    @RequestMapping(value = "/v1/diff/{id}", method = RequestMethod.GET)
    public Diff diff(@PathVariable("id") String id) {
        Result result = diffService.compareFromId(id);
        return new Diff(result.isEqual(), result.getDifferences());
    }
}
