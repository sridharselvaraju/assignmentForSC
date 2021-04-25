package com.sinecycle.assignment.repository;

import com.sinecycle.assignment.entity.TicketResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TicketResponseRepository extends JpaRepository<TicketResponse, Long>, JpaSpecificationExecutor<TicketResponse> {
}
