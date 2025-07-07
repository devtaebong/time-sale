package com.study.timesale.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.study.timesale.domain.TimeSale
import java.time.LocalDateTime

data class PurchaseResponse(
    @field:JsonProperty("timeSaleId") val timeSaleId: Long,
    @field:JsonProperty("userId") val userId: Long,
    @field:JsonProperty("productId") val productId: Long,
    @field:JsonProperty("quantity") val quantity: Long,
    @field:JsonProperty("discountPrice") val discountPrice: Long,
    @field:JsonProperty("purchasedAt") val purchasedAt: LocalDateTime,
) {
    companion object {
        fun from(timeSale: TimeSale, userId: Long, quantity: Long): PurchaseResponse =
            PurchaseResponse(
                timeSaleId = timeSale.id,
                userId = userId,
                productId = timeSale.product.id,
                quantity = quantity,
                discountPrice = timeSale.discountPrice,
                purchasedAt = LocalDateTime.now(),
            )
    }
}
