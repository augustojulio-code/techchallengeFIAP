package com.aquiteturahexa.techchallenge.core.ports.in;

import java.util.List;

import com.aquiteturahexa.techchallenge.core.model.Order;
import com.aquiteturahexa.techchallenge.core.model.Status;

public interface OrderServicePort {
    Order saveOrder(Order order);

    List<Order> findAll();

    Order findById(Long id);

    Order updateOrder(Long id, Order order);

    Order updateStatus(Long id, Order order, Status status);
}
