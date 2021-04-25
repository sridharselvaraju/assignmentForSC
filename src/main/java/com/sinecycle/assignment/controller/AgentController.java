package com.sinecycle.assignment.controller;

import com.sinecycle.assignment.entity.AgentInfo;
import com.sinecycle.assignment.service.AgentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
public class AgentController {
  @NonNull
  private final AgentService agentService;

  @PostMapping("/create")
  public ResponseEntity<?> createAgent(@RequestBody AgentInfo agentInfo) {
    try {
      agentService.createAgent(agentInfo);
      return ResponseEntity.ok().build();
    } catch (Exception ex) {
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/getAgents")
  public ResponseEntity<?> getAgents() {
    List<AgentInfo> agents;
    try {
      agents = agentService.getAgentsByTicketCount();
      return ResponseEntity.ok(agents);
    } catch (Exception ex) {
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
