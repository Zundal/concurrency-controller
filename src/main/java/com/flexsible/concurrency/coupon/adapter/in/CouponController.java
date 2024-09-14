package com.flexsible.concurrency.coupon.adapter.in;

import com.flexsible.concurrency.coupon.domain.entity.Coupon;
import com.flexsible.concurrency.coupon.port.in.CouponUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponUseCase couponUseCase;

    @GetMapping
    public Flux<List<Coupon>> getAllCoupons() {
        return couponUseCase.selectAllCoupons();
    }

    @GetMapping("/page")
    public Mono<ResponseEntity<Flux<Coupon>>> getCouponsByPage(
            @RequestParam Integer page,
            @RequestParam Integer size) {

        return couponUseCase.selectCouponPage(page, size)
                .map(coupons -> ResponseEntity.ok(Flux.fromIterable(coupons)))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Coupon>> getCouponById(
            @PathVariable String id) {

        return couponUseCase.selectCouponById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public Mono<ResponseEntity<Coupon>> getCouponByCode(
            @PathVariable String code) {

        return couponUseCase.selectCouponByCode(code)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Coupon>> createCoupon(
            @RequestBody Coupon coupon) {

        return couponUseCase.createCoupon(coupon)
                .map(createdCoupon -> ResponseEntity.status(HttpStatus.CREATED).body(createdCoupon));
    }

    @PutMapping
    public Mono<ResponseEntity<Coupon>> updateCoupon(
            @RequestBody Coupon coupon) {

        return couponUseCase.updateCoupon(coupon)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Boolean>> deleteCoupon(
            @PathVariable String id) {

        return couponUseCase.deleteCoupon(id)
                .then(Mono.fromCallable(() -> ResponseEntity.ok(true)));

    }

    @DeleteMapping("/code/{code}")
    public Mono<ResponseEntity<Boolean>> deleteCouponByCode(
            @PathVariable String code) {

        return couponUseCase.deleteCouponByCode(code)
                .then(Mono.fromCallable(() -> ResponseEntity.ok(true)));
    }

    @DeleteMapping
    public Mono<ResponseEntity<Boolean>> deleteAllCoupons() {
        return couponUseCase.deleteAllCoupons()
                .then(Mono.fromCallable(() -> ResponseEntity.ok(true)));
    }
}
