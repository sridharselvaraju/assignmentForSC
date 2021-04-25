package com.sinecycle.assignment.service;

import com.sinecycle.assignment.entity.AgentInfo;
import com.sinecycle.assignment.repository.AgentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.sinecycle.assignment.util.Constants.*;

@Component
@Transactional
@RequiredArgsConstructor
public class AgentService {

  @NonNull
  private final AgentRepository agentRepository;

  public AgentInfo updateAssigneeInfo(String status, AgentInfo agentInfo, boolean negativeIndicator, boolean canSave) {

    switch (status) {
      case OPEN:
        Long openCount = agentInfo.getOpenCount();
        if (!negativeIndicator && openCount > 0) {
          agentInfo.setOpenCount(openCount - 1);
        } else {
          agentInfo.setOpenCount(openCount + 1);
        }
        break;
      case RESOLVED:
        Long resolvedCount = agentInfo.getResolvedCount();
        if (!negativeIndicator && resolvedCount > 0) {
          agentInfo.setResolvedCount(resolvedCount - 1);
        } else {
          agentInfo.setResolvedCount(resolvedCount + 1);
        }
        break;
      case CLOSED:
        Long closedCount = agentInfo.getClosedCount();
        if (!negativeIndicator && closedCount > 0) {
          agentInfo.setClosedCount(closedCount - 1);
        } else {
          agentInfo.setClosedCount(closedCount + 1);
        }
        break;
      default:
        Long inProgressCount = agentInfo.getInProgressCount();
        if (!negativeIndicator && inProgressCount > 0) {
          agentInfo.setInProgressCount(inProgressCount - 1);
        } else {
          agentInfo.setInProgressCount(inProgressCount + 1);
        }
        break;
    }

    Long ticketCount = agentInfo.getTicketsCount();
    if (!negativeIndicator) {
      agentInfo.setTicketsCount(ticketCount - 1);
    } else {
      agentInfo.setTicketsCount(ticketCount + 1);
    }

    if (canSave) {
      updateAgent(agentInfo);
    }

    return agentInfo;
  }

  public Optional<AgentInfo> createAgent(AgentInfo agent) {
    return Optional.ofNullable(agentRepository.save(agent));
  }

  public void updateAgent(AgentInfo agent) {
    if (findById(agent.getId()).isPresent()) {
      agentRepository.save(agent);
    }
  }

  public Optional<AgentInfo> findById(Long id) {
    return agentRepository.findById(id);
  }

  public List<AgentInfo> getAgentsByTicketCount() {
    return agentRepository.findAll(Sort.by(Sort.Direction.ASC, "inProgressCount"));
  }
}
