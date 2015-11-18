package com.theironyard.services;

import com.theironyard.entities.AnonFile;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Jack on 11/18/15.
 */
public interface AnonFileRepository extends PagingAndSortingRepository<AnonFile, Integer> {
    AnonFile findFirstByOrderByUploadTimeAsc();
}
