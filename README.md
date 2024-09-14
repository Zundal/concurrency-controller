# 동시성에 대해서

## 낙관적 락

 > 큰 맥락: 
 > > 낙관적 락은 데이터에 대한 충돌이 드물다고 가정하고, 충돌이 발생할 경우에만 처리합니다. 
 > > 데이터가 수정될 가능성이 낮거나 충돌을 처리할 방법이 있는 경우 유용합니다.

 > 자세한 설명: 
 > > 예를 들어, 책을 빌릴 때 대출 중인 책이 다른 사람에 의해 대출되지 않을 것이라고 가정합니다. 
 > >  책이 다른 사람이 대출 중이라면, 대출 요청이 실패할 것입니다. 
 > > 데이터베이스에서 낙관적 락은 버전 번호나 타임스탬프를 사용하여 충돌 여부를 확인하고, 
 > > 충돌이 발생하면 롤백하거나 다시 시도하도록 합니다.

 > 비유: 
 > > 도서관에서 책을 빌릴 때, 책의 상태를 확인하지 않고 빌리려고 하는 것과 같습니다. 
 > > 만약 다른 사람이 책을 대출 중이면, 시스템은 이를 감지하고 대출 요청을 실패로 처리합니다.

## 비관적 락

 > 큰 맥락: 
 > > 비관적 락은 데이터가 충돌할 가능성이 높다고 가정하고, 
 > 데이터에 대한 접근을 미리 잠가서 다른 스레드가 접근하지 못하도록 합니다.

 > 자세한 설명: 
 > > 예를 들어, 공용의 문서를 편집할 때, 문서를 열면 다른 사람이 동시에 열 수 없도록 
 > > 잠금 장치를 거는 것입니다. 
 > > 이렇게 하면 동시에 편집하여 충돌이 발생하는 것을 방지할 수 있습니다. 
 > > 데이터베이스에서는 SELECT ... FOR UPDATE와 같은 쿼리를 사용하여 비관적 락을 구현합니다.

 > 비유: 
 > > 공용 회의실에서 여러 사람이 동시에 회의실을 예약하려 할 때, 
 > > 회의실의 예약 가능 여부를 체크하고, 한 사람이 예약하면 다른 사람은 예약할 수 
 > > 없도록 잠금 장치를 설치하는 것입니다.

## redis 를 이용한 방법

 > 큰 맥락: 
 > > Redis와 같은 외부 저장소는 빠른 접근 속도와 높은 동시성 처리를 제공하며, 
 > > 캐싱 및 분산 락 관리 등 다양한 용도로 사용됩니다.

 > 자세한 설명: 
 > > 예를 들어, 데이터베이스에 대한 요청이 너무 많아 지연이 발생할 때, 
 > > Redis를 캐시로 사용하여 자주 조회되는 데이터를 빠르게 제공하고, 
 > > 분산 락을 통해 여러 인스턴스 간의 동기화를 관리할 수 있습니다.

 > 비유: 
 > > 대형 쇼핑몰에서 고객이 자주 찾는 제품 정보를 빠르게 제공하기 위해 매장에 
 > > 별도의 정보 디스플레이를 설치하는 것과 같습니다. 고객이 특정 제품을 검색하면, 
 > > 매장에 있는 디스플레이에서 빠르게 정보를 제공받을 수 있습니다.


...
동시성 제어와 데이터 일관성을 유지하기 위한 방법들은 다양하며, 각 방법은 특정 상황에서 장점이 있을 수 있습니다. 아래는 이미 언급된 방법 외에도 세련되고 효율적인 방법들입니다:

### 1. **이벤트 소싱 (Event Sourcing)**

**큰 맥락:** 이벤트 소싱은 상태 변경을 직접 저장하는 대신, 상태 변경 이벤트를 저장합니다. 이렇게 하면 모든 상태 변경 이력을 보존할 수 있어 재구성 가능성과 일관성을 보장합니다.

**자세한 설명:** 예를 들어, 은행 계좌의 잔액을 기록하는 대신 모든 입금 및 출금 이벤트를 기록합니다. 특정 시점의 계좌 잔액을 계산하려면 모든 이벤트를 재생(replay)하면 됩니다.

**비유:** 과거의 모든 일기를 기록해 두는 것과 같습니다. 현재의 상태를 알고 싶다면, 과거의 모든 일기를 다시 읽어야 합니다.

### 2. **CQRS (Command Query Responsibility Segregation)**

**큰 맥락:** CQRS는 명령(Command)과 조회(Query) 작업을 분리하여 시스템의 성능과 확장성을 향상시키는 패턴입니다. 쓰기 작업과 읽기 작업이 서로 다른 모델을 사용하므로 더 효율적인 데이터 처리와 스케일링이 가능합니다.

**자세한 설명:** 예를 들어, 대형 온라인 쇼핑몰에서는 주문을 처리하는 명령(Command)과 제품 정보를 조회하는 쿼리(Query)를 분리하여 각각 최적화된 방식으로 처리할 수 있습니다.

**비유:** 도서관에서 책을 대출할 때와 책의 목록을 검색할 때를 구분하여 처리하는 것과 같습니다. 대출과 검색이 서로 다른 시스템에서 처리되기 때문에 더 효율적입니다.

### 3. **사전 동기화 (Preemptive Synchronization)**

**큰 맥락:** 사전 동기화는 동시성 문제가 발생할 수 있는 모든 지점에서 미리 동기화 작업을 수행하는 방식입니다. 이는 예상되는 충돌을 줄이고 시스템의 안정성을 높이는 방법입니다.

**자세한 설명:** 예를 들어, 여러 사용자가 동시에 데이터를 수정하려 할 때, 수정 작업이 시작되기 전에 데이터를 잠그고, 수정이 완료된 후에 잠금을 해제하는 방식입니다.

**비유:** 여러 사람이 동시에 공용 문서에 접근할 때, 문서에 접근하기 전에 문서를 예약해두고, 작업이 끝난 후 예약을 해제하는 것과 같습니다.

### 4. **수정 가능한 복제본 (Mutable Replica)**

**큰 맥락:** 데이터의 복제본을 여러 노드에 유지하고, 수정 가능한 복제본을 사용하여 데이터 일관성을 유지합니다. 이는 데이터의 읽기와 쓰기 작업을 분산시켜 성능을 향상시킵니다.

**자세한 설명:** 예를 들어, 여러 서버에 데이터의 복사본을 유지하고, 데이터의 복사본 중 하나에서만 수정 작업을 수행하도록 하여 충돌을 줄입니다.

**비유:** 여러 도서관에 동일한 책이 있으며, 각 도서관에서 책을 대출할 수 있지만, 각 도서관에서 책의 상태를 독립적으로 관리하는 것과 같습니다.

### 5. **TAP (Transaction Atomicity Protocol)**

**큰 맥락:** TAP는 분산 트랜잭션을 관리하는 프로토콜로, 트랜잭션의 원자성을 보장하며, 여러 데이터베이스에서의 트랜잭션을 일관되게 처리합니다.

**자세한 설명:** 예를 들어, 은행에서 두 개의 계좌 간의 송금을 처리할 때, 두 계좌 모두에서 트랜잭션이 성공적으로 완료되도록 보장합니다.

**비유:** 두 사람 간의 거래를 할 때, 거래의 모든 단계를 성공적으로 완료하지 않으면 거래를 취소하는 것과 같습니다.

### 결론

각 방법은 특정 요구사항과 시스템의 특성에 따라 장단점이 있습니다. 시스템의 요구와 제약을 고려하여 적절한 방법을 선택하고 적용하는 것이 중요합니다. 예를 들어, 이벤트 소싱과 CQRS는 복잡한 시스템에서 상태 관리와 확장성 문제를 해결하는 데 유용하며, 사전 동기화와 수정 가능한 복제본은 실시간 동기화와 성능 최적화에 효과적입니다. TAP은 분산 트랜잭션을 안전하게 처리하는 데 도움을 줄 수 있습니다.