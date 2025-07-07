package com.study.timesale.repository

import com.study.timesale.domain.TimeSaleOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TimeSaleOrderRepository : JpaRepository<TimeSaleOrder, Long>
