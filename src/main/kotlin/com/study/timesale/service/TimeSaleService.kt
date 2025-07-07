package com.study.timesale.service

import com.study.timesale.domain.TimeSale
import com.study.timesale.domain.TimeSaleOrder
import com.study.timesale.domain.TimeSaleStatus
import com.study.timesale.dto.request.CreateTimeSaleRequest
import com.study.timesale.dto.request.PurchaseRequest
import com.study.timesale.repository.ProductRepository
import com.study.timesale.repository.TimeSaleOrderRepository
import com.study.timesale.repository.TimeSaleRepository
import com.study.timesale.repository.getProductById
import com.study.timesale.repository.getTimeSaleById
import com.study.timesale.repository.getTimeSaleByIdWithPessimisticLock
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TimeSaleService(
    private val timeSaleRepository: TimeSaleRepository,
    private val productRepository: ProductRepository,
    private val timeSaleOrderRepository: TimeSaleOrderRepository,
) {

    @Transactional
    fun create(request: CreateTimeSaleRequest): TimeSale {
        val foundProduct = productRepository.getProductById(request.productId)
        validateTimeSale(request.quantity, request.discountPrice, request.startAt, request.endAt)

        return TimeSale(
            product = foundProduct,
            quantity = request.quantity,
            remainingQuantity = request.quantity,
            discountPrice = request.discountPrice,
            startAt = request.startAt,
            endAt = request.endAt,
            status = TimeSaleStatus.ACTIVE,
        ).also { timeSaleRepository.save(it) }
    }

    private fun validateTimeSale(
        quantity: Long,
        discountPrice: Long,
        startAt: LocalDateTime,
        endAt: LocalDateTime,
    ) {
        require(!startAt.isAfter(endAt)) { "startAt must be before endAt: startAt=$startAt, endAt=$endAt" }
        require(quantity > 0) { "quantity must be greater than zero: quantity=$quantity" }
        require(discountPrice > 0) { "discountPrice must be greater than zero: discountPrice=$discountPrice" }
    }

    @Transactional(readOnly = true)
    fun getTimeSale(timeSaleId: Long): TimeSale = timeSaleRepository.getTimeSaleById(timeSaleId)

    @Transactional(readOnly = true)
    fun getOngoingTimeSale(pageable: Pageable): Page<TimeSale> {
        val now = LocalDateTime.now()
        return timeSaleRepository.findAllByStartAtBeforeAndEndAtAfterAndStatus(now, TimeSaleStatus.ACTIVE, pageable)
    }

    @Transactional
    fun purchaseTimeSale(timeSaleId: Long, request: PurchaseRequest): TimeSale {
        val foundTimeSale = timeSaleRepository.getTimeSaleByIdWithPessimisticLock(timeSaleId)

        foundTimeSale.purchase(request.quantity)
        timeSaleRepository.save(foundTimeSale)

        val savedTimeSaleOrder = TimeSaleOrder(
            userId = request.userId,
            timeSale = foundTimeSale,
            quantity = request.quantity,
            discountPrice = foundTimeSale.discountPrice,
        ).let { timeSaleOrderRepository.save(it) }

        savedTimeSaleOrder.complete()
        return foundTimeSale
    }
}
