package org.example.companytech.repo;

import org.example.companytech.domain.ExportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExportHistoryRepository extends JpaRepository<ExportHistory, Long> {
}