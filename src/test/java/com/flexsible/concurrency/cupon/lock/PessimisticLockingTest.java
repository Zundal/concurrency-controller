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
public class PessimisticLockingTest {

    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        // Prepare test data
        Coupon coupon = Coupon.builder()
                .id("test-coupon-id")
                .name("Test Coupon")
                .code("TESTCODE")
                .type("DISCOUNT")
                .limitedQuantity(100)
                .build();

        couponRepository.save(coupon).block();
    }

    @Test
    void testPessimisticLocking() {
        Mono<Void> update1 = couponRepository.findById("test-coupon-id")
                .flatMap(coupon -> {
                    Coupon updatedCoupon = Coupon.builder()
                            .id(coupon.getId())
                            .name(coupon.getName())
                            .code(coupon.getCode())
                            .type(coupon.getType())
                            .limitedQuantity(150)
                            .build();
                    return Mono.delay(Duration.ofSeconds(5))
                            .then(couponRepository.save(updatedCoupon));
                })
                .then();

        Mono<Void> update2 = couponRepository.findById("test-coupon-id")
                .flatMap(coupon -> {
                    Coupon updatedCoupon = Coupon.builder()
                            .id(coupon.getId())
                            .name(coupon.getName())
                            .code(coupon.getCode())
                            .type(coupon.getType())
                            .limitedQuantity(200)
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
