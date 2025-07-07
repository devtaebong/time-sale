package com.study.timesale.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.time.LocalDateTime

data class CreateTimeSaleRequest(
    @field:JsonProperty("productId")
    @NotNull(message = "ProductId cannot be null")
    val productId: Long,

    @field:JsonProperty("quantity")
    @Positive(message = "Quantity must be positive")
    val quantity: Long,

    @field:JsonProperty("discountPrice")
    @Positive(message = "Discount Price must be positive")
    val discountPrice: Long,

    @field:JsonProperty("discountPrice")
    @NotNull(message = "Start time cannot be null")
    @FutureOrPresent(message = "Start time must be current time or in the future")
    val startAt: LocalDateTime,

    @field:JsonProperty("endAt")
    @NotNull(message = "End time cannot be null")
    @Future(message = "End time must be greater than current time")
    val endAt: LocalDateTime,
)
