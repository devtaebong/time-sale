package com.study.timesale.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.study.timesale.domain.Product
import java.time.LocalDateTime

data class ProductResponse(
    @field:JsonProperty("id") val id: Long,
    @field:JsonProperty("name") val name: String,
    @field:JsonProperty("price") val price: Long,
    @field:JsonProperty("description") val description: String,
    @field:JsonProperty("createdAt") val createdAt: LocalDateTime,
) {
    companion object {
        fun from(product: Product): ProductResponse {
            return ProductResponse(
                id = product.id,
                name = product.name,
                price = product.price,
                description = product.description,
                createdAt = product.createdAt,
            )
        }
    }
}
