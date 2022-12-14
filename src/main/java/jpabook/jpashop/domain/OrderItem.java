package jpabook.jpashop.domain;

import jpabook.jpashop.domain.Item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;//주문가격
    private int count;//주문수량

    //비즈니스 로직//
    /**
     * 상품 취소
     * **/
    public void cancel() {
        getItem().addStock(count);

    }

    public int getTotalPrice(){
        return getOrderPrice()*getCount();

    }

//    protected OrderItem(){
//
//    }//생성자 호출이 불가능하도록 만듦 -> lombok이용

    //생성메서드//
    public static OrderItem createOrderItems(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);//아이템의 재고를 줄여야함
        return orderItem;
    }

}
