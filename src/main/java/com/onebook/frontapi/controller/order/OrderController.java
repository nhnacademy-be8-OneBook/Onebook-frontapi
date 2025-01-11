package com.onebook.frontapi.controller.order;

import com.onebook.frontapi.domain.order.OrderProduct;
import com.onebook.frontapi.domain.order.OrderProducts;
import com.onebook.frontapi.dto.book.BookDTO;
import com.onebook.frontapi.dto.delivery.DeliveryRequest;
import com.onebook.frontapi.dto.order.*;
import com.onebook.frontapi.service.book.BookService;
import com.onebook.frontapi.service.member.MemberService;
import com.onebook.frontapi.service.order.OrderAddressService;
import com.onebook.frontapi.service.order.OrderService;
import com.onebook.frontapi.service.order.OrderStatusService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final OrderAddressService orderAddressService;
    private final OrderStatusService orderStatusService;
    private final BookService bookService;

    @GetMapping("/order/order-test")
    public String orderTest() {
        return "order/order-test";
    }

    @PostMapping("/order/registers")
    public String orderRegistesr(@ModelAttribute OrderProducts orderProducts, Model model) {
        // 책 리스트
        Map<BookDTO, Integer> bookMap = new HashMap<>();
        for (OrderProduct orderProduct : orderProducts.getOrderProducts()) {
            bookMap.put(bookService.getBook(orderProduct.getProductId()), orderProduct.getQuantity());
        }
        model.addAttribute("bookMap", bookMap);

        // 사용자의 전화번호
        String orderderPhoneNumber = memberService.getMember().phoneNumber();
        model.addAttribute("ordererPhoneNumber", orderderPhoneNumber);

        List<OrderAddressResponseDto> allOrderAddress = orderAddressService.getAllOrderAddress();
        model.addAttribute("allOrderAddress", allOrderAddress);

        // 배송 선택 날짜
        // TODO utils에 넘기고싶음
        List<Map<String, String>> reservationDates = new ArrayList<>(4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");
        for (int i = 1; i < 4; i++) {
            LocalDate day = LocalDate.now().plusDays(i + 1);

            reservationDates.add(Map.of(
                    "orderNum", String.valueOf(i),
                    "completedDate", String.valueOf(day),
                    "description", day.format(formatter)
            ));
        }
        model.addAttribute("reservationDates", reservationDates);

        if (allOrderAddress.getFirst().isDefaultLocation()) {
            model.addAttribute("orderAddressDefaultLocation", allOrderAddress.getFirst());
        }

        return "order/order";
    }

    @PostMapping("/order/register")
    public String submitOrder(@ModelAttribute OrderRequest orderRequest, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // 전체 요청 파라미터 출력
        request.getParameterMap().forEach((key, value) -> {
            log.info("Parameter - {} : {}", key, Arrays.toString(value));
        });

        // OrderRequest 객체 상태 출력
        log.info("OrderRequest items: {}", orderRequest.getItems());
        if (orderRequest.getItems() != null) {
            orderRequest.getItems().forEach(item -> {
                log.info("Book item - id: {}, quantity: {}, title: {}",
                        item.getBookId(), item.getQuantity(), item.getPrice());
            });
        }
        //        // 데이터를 리다이렉트할 페이지로 전달
//        redirectAttributes.addFlashAttribute("orderSuccess", true);
//        redirectAttributes.addFlashAttribute("orderDetails", orderRegisterResponseDto);

//        Long createOrderId = orderService.createOrder(orderRegisterResponseDto);

        //        return "redirect:/order/success"; // 등록 성공 페이지로 이동
//        return "redirect:/front/payments/checkout-page?orderId=" + createOrderId;
        return "redirect:/order/order-test";
    }

    @GetMapping("/order/success")
    public String showOrderSuccess(Model model) {
        if (model.containsAttribute("orderSuccess")) {
//             성공 메시지나 주문 정보 처리
            OrderRegisterResponseDto orderDetails = (OrderRegisterResponseDto) model.getAttribute("orderDetails");
            System.out.println("주문 성공! " + orderDetails);
        }
        return "order/orderSuccess"; // 주문 성공 페이지 반환
    }

    @GetMapping("/admin/orders")
    public String orderStatus(Model model, @RequestParam String status) {
        model.addAttribute("status", status);

        List<String> orderStatusList = orderStatusService.getAllOrderStatuses();
        model.addAttribute("orderStatusList", orderStatusList);

        List<OrderByStatusResponseDto> ordersByStatus = orderService.getOrdersByStatus(status);
        model.addAttribute("ordersByStatus", ordersByStatus);

        return "admin/orderStatus";
    }

    @PostMapping("/admin/update-orderstatus")
    public String orderStatus(@RequestParam("orderIds") List<Long> orderIds, @RequestParam("preStatus") String preStatus, String postStatus) {
        orderService.updateOrderStatus(orderIds, postStatus);

        // 상태 값 URL 인코딩
        String encodedStatus = URLEncoder.encode(preStatus, StandardCharsets.UTF_8);

        return "redirect:/admin/orders?status=" + encodedStatus;
    }
}
