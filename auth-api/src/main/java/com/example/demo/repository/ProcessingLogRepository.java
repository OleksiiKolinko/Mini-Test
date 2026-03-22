package com.example.demo.repository;

import com.example.demo.model.ProcessingLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessingLogRepository extends JpaRepository<ProcessingLog, UUID> {
}
