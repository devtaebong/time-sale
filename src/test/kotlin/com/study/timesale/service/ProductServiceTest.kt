package com.study.timesale.service

import com.study.timesale.domain.Product
import com.study.timesale.dto.request.CreateProductRequest
import com.study.timesale.repository.ProductRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*
import kotlin.NoSuchElementException

class ProductServiceTest : DescribeSpec({
    lateinit var sut: ProductService
    lateinit var productRepository: ProductRepository

    lateinit var request: CreateProductRequest
    lateinit var product: Product

    beforeTest {
        productRepository = mockk()
        sut = ProductService(productRepository)
    }

    beforeTest {
        request = CreateProductRequest(
            name = "Test Product Name",
            price = 10_000L,
            description = "Test Product Description",
        )

        product = Product(
            id = 1,
            name = request.name,
            price = request.price,
            description = request.description,
        )
    }

    describe("create product test") {
        it("상품을 생성한다") {
            every { productRepository.save(any()) } returns product

            val actual = sut.create(request)

            actual shouldNotBe null
            actual.name shouldBe request.name
            actual.price shouldBe request.price
            actual.description shouldBe request.description
            verify(exactly = 1) { productRepository.save(any()) }
        }
    }

    describe("get product test") {
        it("상품을 조회한다") {
            val productId = 1L
            every { productRepository.findById(any()) } returns Optional.ofNullable(product)

            val actual = sut.getProduct(productId)
            actual shouldNotBe null
            actual.id shouldBe productId
        }

        context("존재하지 않는 상품을 조회하면") {
            it("예외를 던진다") {
                val productId = -1L
                every { productRepository.findById(any()) } returns Optional.empty()

                assertThrows<NoSuchElementException> { sut.getProduct(productId) }
            }
        }
    }
})
