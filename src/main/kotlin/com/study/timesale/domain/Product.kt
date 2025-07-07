package com.study.timesale.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener::class)
class Product(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val price: Long,

    @Column(nullable = false, columnDefinition = "TEXT")
    val description: String,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime,

    @LastModifiedDate
    @Column(nullable = false)
    val updatedAt: LocalDateTime,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
)
