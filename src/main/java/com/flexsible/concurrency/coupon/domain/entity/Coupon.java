package com.flexsible.concurrency.coupon.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Builder
@ToString
@Table("COUPON")
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    private String id;

    private String name;

    private String code;

    private String type;

    private Integer limitedQuantity;

    @Version
    private Long version;
}
