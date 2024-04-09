package com.nhnacademy.delivery.order.repository;

import com.nhnacademy.delivery.order.dto.response.OrderListForAdminResponseDto;
import com.nhnacademy.delivery.order.dto.response.OrderListResponseDto;
import com.nhnacademy.delivery.order_detail.domain.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface OrderRepositoryCustom {
    /**
     * 모든 주문을 반환합니다.
     *
     * @param pageable 페이징.
     * @return OrderListForAdminResponseDto 모든 주문을 반환.
     */
    Page<OrderListForAdminResponseDto> getOrderList(Pageable pageable);

    /**
     * 멤버의 모든 주문을 반환합니다.
     *
     * @param pageable 페이징.
     * @param customerNo 고객 번호.
     * @return OrderListResponseDto 멤버의 모든 주문 반환.
     */
    Page<OrderListResponseDto> getOrdersListByCustomer(Pageable pageable, Long customerNo);






}