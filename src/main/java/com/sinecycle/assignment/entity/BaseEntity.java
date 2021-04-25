package com.sinecycle.assignment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

  @Column(name = "CREATE_DATE", nullable = false)
  private LocalDateTime createDate;

  @Column(name = "CHANGE_DATE")
  private LocalDateTime changeDate;

  @PrePersist
  void onCreate() {
    this.setCreateDate(LocalDateTime.now());
    this.setChangeDate(LocalDateTime.now());
  }

  @PreUpdate
  void onPersist() {
    this.setChangeDate(LocalDateTime.now());
  }
}
