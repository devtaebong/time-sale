package com.study.timesale.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.study.timesale.domain.TimeSale
import java.time.LocalDateTime

data class TimeSaleResponse(
    @field:JsonProperty("id") val id: Long,
    @field:JsonProperty("productId") val productId: Long,
    @field:JsonProperty("quantity") val quantity: Long,
    @field:JsonProperty("remainingQuantity") val remainingQuantity: Long,
    @field:JsonProperty("discountPrice") val discountPrice: Long,
    @field:JsonProperty("startAt") val startAt: LocalDateTime,
    @field:JsonProperty("endAt") val endAt: LocalDateTime,
    @field:JsonProperty("createdAt") val createdAt: LocalDateTime,
    @field:JsonProperty("status") val status: String,
) {
    companion object {
        fun from(timeSale: TimeSale): TimeSaleResponse =
            TimeSaleResponse(
                id = timeSale.id,
                productId = timeSale.product.id,
                quantity = timeSale.quantity,
                remainingQuantity = timeSale.remainingQuantity,
                discountPrice = timeSale.discountPrice,
                startAt = timeSale.startAt,
                endAt = timeSale.endAt,
                createdAt = timeSale.createdAt,
                status = timeSale.status.name,
            )
    }
}
