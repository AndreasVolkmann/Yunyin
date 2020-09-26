package me.avo.yunyin.repository

import me.avo.yunyin.entity.DataSource
import me.avo.yunyin.entity.DataSourceKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DataSourceRepository : JpaRepository<DataSource, DataSourceKey>