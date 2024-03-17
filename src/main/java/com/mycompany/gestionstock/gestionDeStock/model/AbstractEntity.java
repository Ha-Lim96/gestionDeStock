package com.mycompany.gestionstock.gestionDeStock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Generated;
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
    private Integer Id;

    @CreatedDate
    @Column(name="createDate", nullable = false)
    @JsonIgnore
    private Instant creattionDate;

    @LastModifiedDate
    @Column(name="lastModifyDate")
    @JsonIgnore
    private Instant lastModifyDate;
}
