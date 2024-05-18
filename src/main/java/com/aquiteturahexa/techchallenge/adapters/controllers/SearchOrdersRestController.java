package com.aquiteturahexa.techchallenge.adapters.controllers;


import com.aquiteturahexa.techchallenge.core.model.Status;
import com.aquiteturahexa.techchallenge.core.model.User;
import com.aquiteturahexa.techchallenge.core.ports.in.SearchOrderPortIn;
import com.aquiteturahexa.techchallenge.core.ports.in.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@RestController
@RequiredArgsConstructor
public class SearchOrdersRestController {

    private static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");
    private final SearchOrderPortIn searchOrderPortIn;
    private final UserServicePort userServicePort;

    @GetMapping(path = "/api/v1/orders")
    public ResponseEntity<?> get(@RequestHeader Map<String, String> headers,
                                 @RequestParam(value = "status", required = false) List<String> status,
                                 @RequestParam(value = "user_id", required = false) Long userId,
                                 @RequestParam(value = "from", required = false) String fromDate,
                                 @RequestParam(value = "to", required = false) String toDate) {


        List<Status> statusEnums = CollectionUtils.isEmpty(status) ? List.of() : status.stream().map(Status::valueOf).toList();
        User user = userId == null ? null : userServicePort.findById(userId);
        LocalDate from = StringUtils.isBlank(fromDate) ? null : LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate to = StringUtils.isBlank(toDate) ? null : LocalDate.parse(toDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        var orders = searchOrderPortIn.search(isNull(from) ? null : from.atStartOfDay().atZone(ZONE_ID),
                isNull(to) ? null : to.atStartOfDay().atZone(ZONE_ID),
                statusEnums,
                user);

        return orders.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(orders);
    }

}
