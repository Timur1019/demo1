package com.example.demo.controller;

import com.example.demo.db.PortfolioItem;
import com.example.demo.repository.PortfolioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioRepository portfolioRepository;

    @Operation(summary = "Add a new portfolio item",
            description = "Adds a new item to the portfolio database and returns the saved item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Portfolio item successfully added"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/add")
    public ResponseEntity<PortfolioItem> addPortfolioItem(@RequestBody PortfolioItem portfolioItem) {
        PortfolioItem savedItem = portfolioRepository.save(portfolioItem);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a portfolio item",
            description = "Deletes a portfolio item by its ID. Returns 404 if the item is not found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Portfolio item successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Portfolio item not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePortfolioItem(@PathVariable Long id) {
        Optional<PortfolioItem> portfolioItem = portfolioRepository.findById(id);
        if (portfolioItem.isPresent()) {
            portfolioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Portfolio item not found with ID: " + id);
        }
    }

    @Operation(summary = "Get all portfolio items",
            description = "Fetches all portfolio items from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Portfolio items fetched successfully")
    })
    @GetMapping("/all")
    public ResponseEntity<List<PortfolioItem>> getAllPortfolioItems() {
        List<PortfolioItem> portfolioItems = portfolioRepository.findAll();
        return ResponseEntity.ok(portfolioItems);
    }
}
