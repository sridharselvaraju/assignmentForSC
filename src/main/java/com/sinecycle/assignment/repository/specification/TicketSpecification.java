package com.sinecycle.assignment.repository.specification;

import com.sinecycle.assignment.entity.Ticket;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class TicketSpecification {

  public static Specification<Ticket> withSearchCriteria(final SearchCriteria searchCriteria) {
    return (root, query, builder) -> {
      if (null == searchCriteria || (null == searchCriteria.getAssignedTo() && null == searchCriteria.getCustomer()
              && null == searchCriteria.getStatus())) {
        return null;
      } else {
        List<Predicate> predicates = new ArrayList<>();

        if (null != searchCriteria.getAssignedTo()) {
          predicates.add(builder.and(builder.like(builder.lower(root.get("assignedTo").get("name")), searchCriteria.getAssignedTo())));
        }

        if (null != searchCriteria.getCustomer()) {
          predicates.add(builder.and(builder.like(builder.lower(root.get("Customer").get("customerName")), searchCriteria.getCustomer())));
        }

        if (null != searchCriteria.getStatus()) {
          predicates.add(builder.and(builder.like(builder.lower(root.get("status")), getContainsLikePattern(searchCriteria.getStatus()))));
        }
        Predicate[] predicatesArray = new Predicate[predicates.size()];
        predicatesArray = predicates.toArray(predicatesArray);
        return builder.and(predicatesArray);
      }
    };
  }
  private static String getContainsLikePattern(String searchTerm) {
    if (searchTerm == null || searchTerm.isEmpty()) {
      return "%";
    } else {
      return "%" + searchTerm.toLowerCase() + "%";
    }
  }
}
