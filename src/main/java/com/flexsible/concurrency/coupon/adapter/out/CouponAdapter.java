package com.flexsible.concurrency.coupon.adapter.out;

import com.flexsible.concurrency.coupon.domain.entity.Coupon;
import com.flexsible.concurrency.coupon.persistant.CouponRepository;
import com.flexsible.concurrency.coupon.port.out.CouponPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponAdapter implements CouponPort {

    private final CouponRepository couponRepository;

    @Override
    public Flux<Coupon> selectAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public Flux<Coupon> selectCouponPage(Integer page, Integer size) {
        return couponRepository.findCouponsByPage(page, page * size);
    }

    @Override
    public Mono<Coupon> selectCouponById(String id) {
        return couponRepository.findById(id);
    }

    @Override
    public Mono<Coupon> selectCouponByCode(String code) {

        return couponRepository.findByCode(code);
    }

    @Override
    public Mono<Coupon> createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public Mono<Coupon> updateCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public Mono<Void> deleteCoupon(String id) {
        return couponRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteCouponByCode(String code) {
        return couponRepository.deleteByCode(code);
    }

    @Override
    public Mono<Void> deleteAllCoupons() {
        return couponRepository.deleteAll();
    }
}
