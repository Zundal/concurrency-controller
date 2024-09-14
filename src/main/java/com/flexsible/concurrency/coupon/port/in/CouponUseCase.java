package com.flexsible.concurrency.coupon.port.in;

import com.flexsible.concurrency.coupon.domain.entity.Coupon;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CouponUseCase {

    Flux<List<Coupon>> selectAllCoupons();

    Mono<List<Coupon>> selectCouponPage(Integer page, Integer size);

    Mono<Coupon> selectCouponById(String id);

    Mono<Coupon> selectCouponByCode(String code);

    Mono<Coupon> createCoupon(Coupon coupon);

    Mono<Coupon> updateCoupon(Coupon coupon);

    Mono<Void> deleteCoupon(String id);

    Mono<Void> deleteCouponByCode(String code);

    Mono<Void> deleteAllCoupons();

}
