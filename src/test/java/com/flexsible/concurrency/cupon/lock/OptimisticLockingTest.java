package com.flexsible.concurrency.cupon.lock;

import com.flexsible.concurrency.coupon.domain.entity.Coupon;
import com.flexsible.concurrency.coupon.persistant.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest
public class OptimisticLockingTest {

    @Autowired
    private CouponRepository couponRepository;

    private String savedCouponId;

    @BeforeEach
    void setUp() {
        Coupon coupon = Coupon.builder()
                .name("Test Coupon")
                .code("TESTCODE-" + System.currentTimeMillis())  // 고유한 코드로 설정
                .type("DISCOUNT")
                .limitedQuantity(100)
                .build();

        savedCouponId = couponRepository.save(coupon)
                .map(Coupon::getId)  // 저장된 쿠폰의 ID를 저장
                .block();
    }

//    @Test
    void testOptimisticLocking() {

        Mono<Void> update1 = couponRepository.findById(savedCouponId)
                .flatMap(coupon -> {
                    Coupon updatedCoupon = Coupon.builder()
                            .id(coupon.getId())
                            .name(coupon.getName())
                            .code(coupon.getCode())
                            .type(coupon.getType())
                            .limitedQuantity(150)
                            .version(coupon.getVersion())
                            .build();
                    return couponRepository.save(updatedCoupon);
                })
                .delayElement(Duration.ofMillis(100))
                .then();

        Mono<Void> update2 = couponRepository.findById(savedCouponId)
                .flatMap(coupon -> {
                    Coupon updatedCoupon = Coupon.builder()
                            .id(coupon.getId())
                            .name(coupon.getName())
                            .code(coupon.getCode())
                            .type(coupon.getType())
                            .limitedQuantity(200)
                            .version(coupon.getVersion())
                            .build();
                    return couponRepository.save(updatedCoupon);
                })
                .then();

        Mono<Void> combinedUpdates = Mono.when(update1, update2);

        StepVerifier.create(combinedUpdates)
                .expectError(OptimisticLockingFailureException.class)
                .verify();
    }
}



