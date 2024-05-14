package com.aquiteturahexa.techchallenge.adapters.persistence;

import com.aquiteturahexa.techchallenge.adapters.persistence.mapper.OrderMapper;
import com.aquiteturahexa.techchallenge.adapters.persistence.mapper.UserMapper;
import com.aquiteturahexa.techchallenge.adapters.persistence.repositories.OrderRepository;
import com.aquiteturahexa.techchallenge.adapters.persistence.specifications.OrderSpecification;
import com.aquiteturahexa.techchallenge.core.model.Order;
import com.aquiteturahexa.techchallenge.core.model.Status;
import com.aquiteturahexa.techchallenge.core.model.User;
import com.aquiteturahexa.techchallenge.core.ports.out.SearchOrderPortOut;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchOrderPersistenceAdapterOut implements SearchOrderPortOut {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> search(ZonedDateTime start,
                              ZonedDateTime end,
                              Status status,
                              User requester) {

        var orders = orderRepository.findAll(Specification
                .where(OrderSpecification.hasRequestedAtBetween(start, end))
                .and(OrderSpecification.hasStatus(status.name()))
                .and(OrderSpecification.hasRequester(UserMapper.toEntity(requester))));

        return orders
                .stream()
                .map(OrderMapper::toDomain)
                .toList();
    }
}