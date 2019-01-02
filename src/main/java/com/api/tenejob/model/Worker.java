package com.api.tenejob.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * Created by p4pupro on 20/12/2018.
 */

@Entity
@Table(name = "worker")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)

public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String [] availability;

    private Float payrate;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;


    public Worker() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPayrate() {
        return payrate;
    }

    public void setPayrate(Float payrate) {
        this.payrate = payrate;
    }

    public String[] getAvailability() {
        return availability;
    }

    public void setAvailability(String[] availability) {
        this.availability = availability;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", availability=" + Arrays.toString(availability) +
                ", payrate=" + payrate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return Objects.equals(id, worker.id) &&
                Arrays.equals(availability, worker.availability) &&
                Objects.equals(payrate, worker.payrate) &&
                Objects.equals(createdAt, worker.createdAt) &&
                Objects.equals(updatedAt, worker.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, payrate, createdAt, updatedAt);
        result = 31 * result + Arrays.hashCode(availability);
        return result;
    }
}
