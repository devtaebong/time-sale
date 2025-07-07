package com.study.timesale.service

import com.study.timesale.domain.Product
import com.study.timesale.dto.request.CreateProductRequest
import com.study.timesale.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    @Transactional
    fun create(request: CreateProductRequest): Product {
        val product = request.toProduct()
        return productRepository.save(product)
    }
}
