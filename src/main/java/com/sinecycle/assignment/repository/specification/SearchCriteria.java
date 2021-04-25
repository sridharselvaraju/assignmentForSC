package com.sinecycle.assignment.repository.specification;

import lombok.Data;
@Data
public class SearchCriteria {
  private String assignedTo;
  private String customer;
  private String status ;
}
