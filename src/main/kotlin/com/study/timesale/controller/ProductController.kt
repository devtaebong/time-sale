package com.study.timesale.controller

import com.study.timesale.dto.request.CreateProductRequest
import com.study.timesale.dto.response.ProductResponse
import com.study.timesale.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService,
) {
    @PostMapping
    fun createProduct(@RequestBody @Valid request: CreateProductRequest): ResponseEntity<ProductResponse> {
        val product = productService.create(request)
        return ResponseEntity.ok(ProductResponse.from(product))
    }

    @GetMapping("/{productId}")
    fun getProduct(@PathVariable productId: Long): ResponseEntity<ProductResponse> {
        val product = productService.getProduct(productId)
        return ResponseEntity.ok(ProductResponse.from(product))
    }
}
