package com.github.services.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.services.model.History;
import com.github.services.repositories.HistoryRepository;

@RestController(value="")
@RequestMapping(path="/history")
public class ApiHistory {
	
	@Autowired
	private HistoryRepository historyRepo;
	
	/**
	 * Get All History
	 * @param name
	 * @return
	 */
    @RequestMapping(method=RequestMethod.GET)
    public List<History> getHistory(@RequestParam(value="name", defaultValue="World") String name) {
        return (List<History>) historyRepo.findAll();
    }
    
    /**
     * Get History per Api
     * @param api
     * @return
     */
    @RequestMapping(path="/{api}", method=RequestMethod.GET)
    public List<History> getHistoryByApi(@PathVariable("api") String api) {
        return (List<History>) historyRepo.findByApi(api);
    }
	
    /**
     * Save new History request.
     * @param history
     * @return
     */
	@RequestMapping(method=RequestMethod.POST)
    public History saveHistory(@RequestBody History history) {
        return historyRepo.save(history);
    }
	
	/**
	 * Delete History request by ID.
	 * @param id
	 */
	@RequestMapping(path="/{id}", method=RequestMethod.DELETE)
    public void deleteHistory(@Valid @PathVariable("id") String id) {
		if(id.equalsIgnoreCase("all")){
			historyRepo.deleteAll();
		}else{
			historyRepo.delete(id);
		}
    }
}