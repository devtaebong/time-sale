package com.study.timesale.service

import com.study.timesale.domain.Product
import com.study.timesale.domain.TimeSale
import com.study.timesale.domain.TimeSaleStatus
import com.study.timesale.dto.request.CreateTimeSaleRequest
import com.study.timesale.repository.ProductRepository
import com.study.timesale.repository.TimeSaleOrderRepository
import com.study.timesale.repository.TimeSaleRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.*

class TimeSaleServiceTest : DescribeSpec({
    lateinit var sut: TimeSaleService
    lateinit var timeSaleRepository: TimeSaleRepository
    lateinit var productRepository: ProductRepository
    lateinit var timeSaleOrderRepository: TimeSaleOrderRepository

    lateinit var now: LocalDateTime
    lateinit var product: Product
    lateinit var timeSale: TimeSale
    lateinit var timeSaleCreateRequest: CreateTimeSaleRequest

    beforeTest {
        timeSaleRepository = mockk()
        productRepository = mockk()
        timeSaleOrderRepository = mockk()
        timeSaleRepository = mockk()

        sut = TimeSaleService(
            timeSaleRepository = timeSaleRepository,
            productRepository = productRepository,
            timeSaleOrderRepository = timeSaleOrderRepository,
        )
    }

    beforeTest {
        now = LocalDateTime.now()

        product = Product(
            name = "테스트 상품",
            price = 20_000L,
            description = "테스트용 상품 설명입니다.",
            id = 1L,
            createdAt = now.minusDays(1),
            updatedAt = now
        )

        timeSaleCreateRequest = CreateTimeSaleRequest(
            productId = 1L,
            quantity = 10L,
            discountPrice = 10_000L,
            startAt = now.plusHours(1),
            endAt = now.plusHours(2),
        )

        timeSale = TimeSale(
            product = product,
            quantity = timeSaleCreateRequest.quantity,
            remainingQuantity = timeSaleCreateRequest.quantity,
            discountPrice = timeSaleCreateRequest.discountPrice,
            startAt = timeSaleCreateRequest.startAt,
            endAt = timeSaleCreateRequest.endAt,
            status = TimeSaleStatus.ACTIVE,
            createdAt = now,
            updatedAt = now
        )
    }

    describe("create test") {
        it("should create a new product") {
            every { productRepository.findById(1L) } returns Optional.of(product)
            every { timeSaleRepository.save(any()) } returns timeSale

            val actual = sut.create(timeSaleCreateRequest)

            actual shouldNotBe null
            actual.product shouldBe product
            actual.quantity shouldBe timeSaleCreateRequest.quantity
        }
    }
})
