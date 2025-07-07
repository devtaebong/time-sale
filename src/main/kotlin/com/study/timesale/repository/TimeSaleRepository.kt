package com.study.timesale.repository

import com.study.timesale.domain.TimeSale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

fun TimeSaleRepository.getTimeSaleById(id: Long): TimeSale =
    this.findById(id).getOrNull() ?: throw NoSuchElementException("Product not found")

@Repository
interface TimeSaleRepository : JpaRepository<TimeSale, Long>
