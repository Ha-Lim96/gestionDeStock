package com.mycompany.gestionstock.gestionDeStock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
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
