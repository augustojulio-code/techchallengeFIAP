package com.aquiteturahexa.techchallenge.adapters.controllers;

import com.aquiteturahexa.techchallenge.adapters.controllers.dto.ClientDto;
import com.aquiteturahexa.techchallenge.adapters.controllers.dto.ItemDto;
import com.aquiteturahexa.techchallenge.adapters.controllers.mappers.ItemMapper;
import com.aquiteturahexa.techchallenge.core.ports.in.GetItemPortIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "Get Item By Id Controller", description = "Controller for return item data by his id")
public class GetItemRestController {

    private final GetItemPortIn getItemPortIn;

    @GetMapping(path = "/api/v1/items/{id}")
    @Operation(summary = "Return item data by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully returned item data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDto.class))),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<?> getById(@RequestHeader Map<String, String> headers, @PathVariable("id") String id) {

        var item = getItemPortIn.get(Long.parseLong(id));

        return item.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(ItemMapper.toDto(item.get()));

    }

}
