package com.flexsible.concurrency.coupon.port.out;

import com.flexsible.concurrency.coupon.domain.entity.Coupon;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CouponPort {

    Flux<Coupon> selectAllCoupons();

    Flux<Coupon> selectCouponPage(Integer page, Integer size);

    Mono<Coupon> selectCouponById(String id);

    Mono<Coupon> selectCouponByCode(String code);

    Mono<Coupon> createCoupon(Coupon coupon);

    Mono<Coupon> updateCoupon(Coupon coupon);

    Mono<Void> deleteCoupon(String id);

    Mono<Void> deleteCouponByCode(String code);

    Mono<Void> deleteAllCoupons();

}
