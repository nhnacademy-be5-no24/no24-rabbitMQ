package com.nhnacademy.delivery.orders.controller;

import com.nhnacademy.delivery.orders.dto.request.OrdersCreateRequestDto;
import com.nhnacademy.delivery.orders.dto.request.OrdersModifyOrderStateRequestDto;
import com.nhnacademy.delivery.orders.dto.response.OrdersListForAdminResponseDto;
import com.nhnacademy.delivery.orders.dto.response.OrdersResponseDto;
import com.nhnacademy.delivery.orders.exception.OrderStatusFailedException;
import com.nhnacademy.delivery.orders.exception.SaveOrderFailed;

import com.nhnacademy.delivery.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrdersService orderService;


    /**
     * 모든 주문을 반환. (admin)
     * @param pageable 페이징.
     * @return 200 OK, 모든 주문 반환.
     */
    @GetMapping("/admin")
    public ResponseEntity<Page<OrdersListForAdminResponseDto>> getOrders(
            Pageable pageable
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderService.getOrders(pageable));
    }
    /**
     * 고객 번호로 고객의 모든 주문을 반환.
     *
     * @param customerNo 고객 번호.
     * @param pageable 페이징.
     * @return 200, 고객의 모든 주문 반환.
     */
    @GetMapping("/customer/{customerNo}")
    public ResponseEntity<Page<OrdersResponseDto>> getOrdersByCustomer(
            @PathVariable Long customerNo,
            Pageable pageable) {
        Page<OrdersResponseDto> ordersPage = orderService.getOrderByCustomer(pageable, customerNo);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ordersPage);
    }

    /**
     * 주문아이디로 주문 정보를 반환
     *
     * @param orderId 주문 아이디.
     * @return 200 OK, 주문 상세내용을 반환.
     */
    @GetMapping("/orderId/{orderId}")
    public ResponseEntity<OrdersResponseDto> getOrderDetailByOrderId(
            @PathVariable String orderId
    ) {
        OrdersResponseDto responseDto = orderService.getOrderByOrdersId(orderId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

    /**
     *
     * @param ordersCreateRequestDto 주문 등록을 위한 dto.
     * @throws OrderStatusFailedException not found.
     * @throws SaveOrderFailed not found.
     * @return 201 created.
     */
    @PostMapping("/orders")
    public ResponseEntity<String> createOrder(
            @RequestBody OrdersCreateRequestDto ordersCreateRequestDto
            ){
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(orderService.createOrder(ordersCreateRequestDto).getOrderId());
        }catch(OrderStatusFailedException | SaveOrderFailed e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * 주문 상태 바꾸는 method.
     *
     * @param orderId 주문 아이디
     * @param ordersModifyOrderStateRequestDto 주문상태 정보 dto
     *
     * @return 201, created 반환
     */
    @PutMapping("orders/{orderId}/state")
    public ResponseEntity<Void> modifyOrderState(
            @PathVariable String orderId,
            @RequestBody OrdersModifyOrderStateRequestDto ordersModifyOrderStateRequestDto
    ){
        orderService.modifyOrderState(orderId, ordersModifyOrderStateRequestDto.getOrderState());
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }


}