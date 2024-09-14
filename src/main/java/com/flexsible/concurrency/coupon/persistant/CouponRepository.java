package com.flexsible.concurrency.coupon.persistant;

import com.flexsible.concurrency.coupon.domain.entity.Coupon;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CouponRepository
        extends R2dbcRepository<Coupon, String> {

    @Query("SELECT * FROM COUPON LIMIT :limit OFFSET :offset")
    Flux<Coupon> findCouponsByPage(@Param("limit") int limit, @Param("offset") int offset);

    Mono<Coupon> findByCode(String code);

    Mono<Void> deleteByCode(String code);
}
