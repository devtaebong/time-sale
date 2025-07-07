package com.study.timesale.repository

import com.study.timesale.domain.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

fun ProductRepository.getProductById(id: Long): Product =
    this.findById(id).getOrNull() ?: throw NoSuchElementException("Product not found")

@Repository
interface ProductRepository : JpaRepository<Product, Long>
