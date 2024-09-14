DROP TABLE IF EXISTS COUPON;

CREATE TABLE COUPON
(
    id               VARCHAR(36) PRIMARY KEY DEFAULT (UUID()) COMMENT '쿠폰의 고유 식별자',
    name             VARCHAR(255) NOT NULL COMMENT '쿠폰 이름',
    code             VARCHAR(100) NOT NULL UNIQUE COMMENT '쿠폰 코드 (중복 불가)',
    type             VARCHAR(50)  NOT NULL COMMENT '쿠폰의 유형 (예: 할인, 무료배송 등)',
    limited_quantity INT COMMENT '한정 수량 (NULL일 경우 제한 없음)',
    version          BIGINT NOT NULL DEFAULT 0 COMMENT '낙관적 잠금을 위한 버전 관리 필드'
) COMMENT = '쿠폰 정보를 저장하는 테이블';
