package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name="Orders")
@Getter
@Setter
public class Order {
    @Id @GeneratedValue
    @Column(name ="order_id")
    private Long id;

    @ManyToOne(fetch= LAZY) //FK
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy ="order", cascade = ALL) //orderItems의 order column으로 mapping한다는 의미 -> 읽기전용
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch= LAZY, cascade = ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;
    private LocalDateTime orderDate; //DateTime과 달리 annotation이 필요없음

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태로 [order, cancel]로 생성

    //연관관계 편의를 위한 메서드 => 양방향 연관관계 등록시 편의를 위해 작성//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }


}
