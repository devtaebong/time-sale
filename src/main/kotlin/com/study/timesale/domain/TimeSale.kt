package com.study.timesale.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.Version
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "time_sales")
@EntityListeners(AuditingEntityListener::class)
class TimeSale(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    @Column(nullable = false)
    val quantity: Long,

    @Column(nullable = false)
    var remainingQuantity: Long,

    @Column(nullable = false)
    val discountPrice: Long,

    @Column(nullable = false)
    val startAt: LocalDateTime,

    @Column(nullable = false)
    val endAt: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TimeSaleStatus = TimeSaleStatus.ACTIVE,

    @Version
    val version: Long? = null,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) {
    fun purchase(quantity: Long) {
        validatePurchase(quantity)
        this.remainingQuantity -= quantity
    }

    private fun validatePurchase(quantity: Long) {
        validateStatus()
        validateQuantity(quantity)
        validatePeriod()
    }

    private fun validateStatus() {
        check(status == TimeSaleStatus.ACTIVE) { "Time sale is not active" }
    }

    private fun validateQuantity(quantity: Long) {
        check(remainingQuantity >= quantity) { "not enough quantity to purchase time" }
    }

    private fun validatePeriod() {
        val now = LocalDateTime.now()
        check(now.isAfter(startAt) && now.isBefore(endAt)) {
            "Current time ($now) is not within the time sale period: $startAt ~ $endAt"
        }
    }
}
