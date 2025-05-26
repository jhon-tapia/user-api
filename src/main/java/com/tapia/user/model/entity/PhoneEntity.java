package com.tapia.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "user_phone")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(nullable = false, length = 15)
    private String number;

    @Column(nullable = false, length = 5)
    private String citycode;

    @Column(nullable = false, length = 5)
    private String contrycode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
