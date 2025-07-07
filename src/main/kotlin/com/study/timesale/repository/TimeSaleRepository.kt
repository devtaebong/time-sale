package com.study.timesale.repository

import com.study.timesale.domain.TimeSale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TimeSaleRepository : JpaRepository<TimeSale, Long>
