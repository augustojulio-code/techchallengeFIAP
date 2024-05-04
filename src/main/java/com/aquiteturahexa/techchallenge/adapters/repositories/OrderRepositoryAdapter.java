package com.aquiteturahexa.techchallenge.adapters.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.aquiteturahexa.techchallenge.adapters.entities.OrderEntity;
import com.aquiteturahexa.techchallenge.core.model.Order;
import com.aquiteturahexa.techchallenge.core.ports.OrderRepositoryPort;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order saveOrder(Order order) {
        OrderEntity entity = modelMapper.map(order, OrderEntity.class);
        OrderEntity save = orderRepository.save(entity);

        return modelMapper.map(save, Order.class);
    }

    @Override
    public List<Order> findAll() {
        List<OrderEntity> list = orderRepository.findAll();
        return list.stream()
                .map(orderEntity -> modelMapper.map(orderEntity, Order.class))
                .collect(Collectors.toList());
    }

    @Override
    public Order findById(Long id) {
        Optional<OrderEntity> obj = orderRepository.findById(id);

        return obj.map(orderEntity -> modelMapper.map(orderEntity, Order.class))
                .orElse(null);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Order existOrder = findById(id);
        existOrder.setTotalPrice(order.getTotalPrice());
        existOrder.setCombos(order.getCombos());

        return saveOrder(existOrder);
    }

    @Override
    public Order updateStatus(Long id, Order order) {
        Order existOrder = findById(id);
        existOrder.setStatus(order.getStatus());
        return saveOrder(existOrder);
    }

}
