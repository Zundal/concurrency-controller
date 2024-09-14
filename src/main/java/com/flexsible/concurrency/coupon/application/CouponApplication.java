package com.flexsible.concurrency.coupon.application;

import com.flexsible.concurrency.coupon.domain.entity.Coupon;
import com.flexsible.concurrency.coupon.port.in.CouponUseCase;
import com.flexsible.concurrency.coupon.port.out.CouponPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponApplication implements CouponUseCase {

    private final CouponPort couponPort;

    @Override
    public Flux<List<Coupon>> selectAllCoupons() {
        return couponPort.selectAllCoupons().collectList().flux();
    }

    @Override
    public Mono<List<Coupon>> selectCouponPage(Integer page, Integer size) {
        return couponPort.selectCouponPage(page, size)
                .collectList();
    }

    @Override
    public Mono<Coupon> selectCouponById(String id) {
        return couponPort.selectCouponById(id);
    }

    @Override
    public Mono<Coupon> selectCouponByCode(String code) {
        return couponPort.selectCouponByCode(code);
    }

    @Override
    public Mono<Coupon> createCoupon(Coupon coupon) {
        return couponPort.createCoupon(coupon);
    }

    @Override
    public Mono<Coupon> updateCoupon(Coupon coupon) {
        return couponPort.updateCoupon(coupon);
    }

    @Override
    public Mono<Void> deleteCoupon(String id) {
        return couponPort.deleteCoupon(id);
    }

    @Override
    public Mono<Void> deleteCouponByCode(String code) {
        return couponPort.deleteCouponByCode(code);
    }

    @Override
    public Mono<Void> deleteAllCoupons() {
        return couponPort.deleteAllCoupons();
    }
}
