package org.example.companytech.repo;

import java.math.BigDecimal;
import java.util.Optional;
import org.example.companytech.domain.ExportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExportHistoryRepository extends JpaRepository<ExportHistory, Long> {
    @Query(nativeQuery = true,
            value = "select * from export_history" +
                    " where extract(year from date)=:year and extract(month from date)=:month")
    Optional<ExportHistory> findExportHistoryByYearAndMonth(@Param("year") Integer year, @Param("month") Integer month);

    @Query(nativeQuery = true,value = "" +
            "SELECT SUM(public.export_history.count * price) AS total_value FROM export_history inner join public.product p on p.id = export_history.product_id " +
            "where extract(year from date)=:year and extract(month from date)=:month")
    BigDecimal getTotalSum(@Param("year") Integer year, @Param("month") Integer month);
}