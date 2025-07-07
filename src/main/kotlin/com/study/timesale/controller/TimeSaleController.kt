package com.study.timesale.controller

import com.study.timesale.dto.request.CreateTimeSaleRequest
import com.study.timesale.dto.request.PurchaseRequest
import com.study.timesale.dto.response.PurchaseResponse
import com.study.timesale.dto.response.TimeSaleResponse
import com.study.timesale.service.TimeSaleService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/time-sales")
class TimeSaleController(
    private val timeSaleService: TimeSaleService,
) {
    @PostMapping
    fun createTimeSale(@RequestBody request: CreateTimeSaleRequest): ResponseEntity<TimeSaleResponse> {
        val timeSale = timeSaleService.create(request)
        return ResponseEntity.ok(TimeSaleResponse.from(timeSale))
    }

    @GetMapping("/{timeSaleId}")
    fun getTimeSale(@PathVariable timeSaleId: Long): ResponseEntity<TimeSaleResponse> {
        val timeSale = timeSaleService.getTimeSale(timeSaleId)
        return ResponseEntity.ok(TimeSaleResponse.from(timeSale))
    }

    @GetMapping
    fun getOngoingTimeSales(@PageableDefault pageable: Pageable): ResponseEntity<Page<TimeSaleResponse>> {
        val timeSales = timeSaleService.getOngoingTimeSale(pageable)
        return ResponseEntity.ok(timeSales.map { TimeSaleResponse.from(it) })
    }

    @PostMapping("/{timeSaleId}/purchase")
    fun purchaseTimeSale(
        @PathVariable timeSaleId: Long,
        @RequestBody @Valid request: PurchaseRequest,
    ): ResponseEntity<PurchaseResponse> {
        val timeSale = timeSaleService.purchaseTimeSale(timeSaleId, request)
        return ResponseEntity.ok(
            PurchaseResponse.from(timeSale, request.userId, request.quantity)
        )
    }
}
