package com.cleanarchitecture.techchallenge.api.rest.controllers.item;

import com.cleanarchitecture.techchallenge.api.rest.dtos.item.ItemDto;
import com.cleanarchitecture.techchallenge.api.rest.dtos.item.RequestCreateItemDto;
import com.cleanarchitecture.techchallenge.infra.controllers.item.UpdateItemController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "Update Item Controller", description = "Controller for update item and save in database")
public class UpdateItemRestController {

    private final UpdateItemController updateItemController;

    @PutMapping(path = "/api/v1/items/{id}")
    @Operation(summary = "Update Item")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Receive updated item data and save it in the database",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDto.class))),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<?> updateItem(
            @RequestHeader Map<String, String> headers,
            @PathVariable("id") String id,
            @RequestBody RequestCreateItemDto body) {

        var updatedItem = updateItemController.updateItem(Long.parseLong(id), body);

        return updatedItem == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(updatedItem);
    }

}
