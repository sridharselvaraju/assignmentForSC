package com.sinecycle.assignment.repository;

import com.sinecycle.assignment.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
  @Query(value = "SELECT * FROM DTA_TICKET  t WHERE t.status = :status " +
          "and t.change_date >= :fromDate and t.change_date <= :toDate", nativeQuery = true)
  List<Ticket> findByStatus(@Param("status") String status, @Param("fromDate") LocalDateTime date, @Param("toDate") LocalDateTime changeDate);
}
