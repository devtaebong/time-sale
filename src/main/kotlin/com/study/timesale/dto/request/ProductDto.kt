package com.study.timesale.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.study.timesale.domain.Product
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateProductRequest(
    @field:JsonProperty("name")
    @NotBlank(message = "Name can't be empty")
    val name: String,

    @field:JsonProperty("price")
    @NotNull(message = "Price can't be null")
    val price: Long,

    @field:JsonProperty("description")
    @NotBlank(message = "Description can't be empty")
    val description: String,
) {
    fun toProduct(): Product = Product(
        name = name,
        price = price,
        description = description,
    )
}
