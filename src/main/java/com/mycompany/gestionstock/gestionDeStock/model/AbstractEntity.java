package com.mycompany.gestionstock.gestionDeStock.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;

  //@CreatedDate
  @Column(name = "creationDate")
  private Instant creationDate;

  //@LastModifiedDate
  @Column(name = "lastModifiedDate")
  private Instant lastModifiedDate;


  @PrePersist
  void prepersist() {
    creationDate = Instant.now();
  }

  @PreUpdate
  void PreUpdate() {
    lastModifiedDate = Instant.now();
  }

}
