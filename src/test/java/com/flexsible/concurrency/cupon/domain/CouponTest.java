package com.flexsible.concurrency.cupon.domain;

import com.flexsible.concurrency.coupon.domain.entity.Coupon;
import com.flexsible.concurrency.coupon.persistant.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.Random;

@SpringBootTest
class CouponTest {

    @Autowired
    private CouponRepository couponRepository;

    private final Random random = new Random();

    @BeforeEach
    void setUp() {
        Flux<Coupon> coupons = Flux.range(1, 20)
                .map(i -> Coupon.builder()
                        .name("Coupon" + i)
                        .code("CODE" + random.nextInt(1000))
                        .type("DISCOUNT")
                        .limitedQuantity(random.nextInt(500) + 1)
                        .build());

        // 데이터 저장 후 완료될 때까지 대기
        couponRepository.saveAll(coupons)
                .doOnComplete(() -> System.out.println("Coupons saved"))
                .blockLast();  // 저장이 끝날 때까지 기다림
    }

    @Test
    void testFindAllCoupons() {


        // 데이터 조회 테스트
        couponRepository.findAll()
                .doOnNext(System.out::println)  // 결과 출력
                .blockLast();  // 모든 데이터를 처리할 때까지 대기
    }
}
