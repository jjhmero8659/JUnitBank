# JUnit Bank App

### Jpa LocalDateTime 자동으로 LocalDateTime 생성 하는 법
- @EnableJpaAuditing //날짜 기입 어노테이션 , (Main Class 적용)
- @EntityListeners(AuditingEntityListener.class) (Entity Class 적용)

```java
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(nullable = false)
    private LocalDateTime updateAt;
```
 
### 본 프로젝트에서 시간 Entity를 따로 분리하여 상속해서 사용하지 않은 이유는 JUnit 테스트를 편하게 하기 위함