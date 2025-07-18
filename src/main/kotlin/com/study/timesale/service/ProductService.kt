package com.study.timesale.service

import com.study.timesale.domain.Product
import com.study.timesale.dto.request.CreateProductRequest
import com.study.timesale.repository.ProductRepository
import com.study.timesale.repository.getProductById
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

    @Transactional(readOnly = true)
    fun getProduct(productId: Long): Product = productRepository.getProductById(productId)

    @Transactional(readOnly = true)
    fun getAllProducts(): List<Product> = productRepository.findAll()
}
