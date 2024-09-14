package com.flexsible.concurrency.coupon.persistant;

import com.flexsible.concurrency.coupon.domain.entity.Coupon;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository
        extends R2dbcRepository<Coupon, String> {
}
