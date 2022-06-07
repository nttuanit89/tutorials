package com.nttuanit.common.crud;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity implements Serializable {
  protected Timestamp createdTime;
  protected Timestamp updatedTime;

  @Version protected Long version;

  protected String createdBy;
  protected String updatedBy;

  @PrePersist
  private void prePersist() {
    this.createdTime = new Timestamp(System.currentTimeMillis());
    this.updatedTime = new Timestamp(System.currentTimeMillis());
    //    if (SecurityUtil.getCurrentUser() != null) {
    //      createdBy = SecurityUtil.getCurrentUser().getProfileId();
    //    }
  }

  @PreUpdate
  private void preUpdate() {
    this.updatedTime = new Timestamp(System.currentTimeMillis());
    //    if (SecurityUtil.getCurrentUser() != null) {
    //      updatedBy = SecurityUtil.getCurrentUser().getProfileId();
    //    }
  }
}
