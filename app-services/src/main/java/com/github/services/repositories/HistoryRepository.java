package com.github.services.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.github.services.model.History;

/**
 * History repo based in Spring Data. (Memory data)
 * @author omar.yepez
 *
 */
public interface HistoryRepository extends CrudRepository<History, String> {
	  List<History> findByApi(String api);
}
