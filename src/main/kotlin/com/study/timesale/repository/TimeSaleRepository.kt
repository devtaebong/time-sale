package com.study.timesale.repository

import com.study.timesale.domain.TimeSale
import com.study.timesale.domain.TimeSaleStatus
import jakarta.persistence.LockModeType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

fun TimeSaleRepository.getTimeSaleById(id: Long): TimeSale =
    this.findById(id).getOrNull() ?: throw NoSuchElementException("TimeSale not found")

fun TimeSaleRepository.getTimeSaleByIdWithPessimisticLock(id: Long): TimeSale =
    this.findByIdWithPessimisticLock(id) ?: throw NoSuchElementException("Product not found")

@Repository
interface TimeSaleRepository : JpaRepository<TimeSale, Long> {
    @Query("select ts from TimeSale ts where ts.startAt <= :now and ts.endAt >= :now and ts.status = :status")
    fun findAllByStartAtBeforeAndEndAtAfterAndStatus(
        now: LocalDateTime,
        status: TimeSaleStatus,
        pageable: Pageable,
    ): Page<TimeSale>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select ts from TimeSale ts where ts.id = :id")
    fun findByIdWithPessimisticLock(@Param("id") id: Long): TimeSale?
}
