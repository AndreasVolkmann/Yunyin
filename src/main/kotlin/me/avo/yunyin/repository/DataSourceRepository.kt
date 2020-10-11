package me.avo.yunyin.repository

import me.avo.yunyin.entity.DataSourceEntity
import me.avo.yunyin.entity.DataSourceKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DataSourceRepository : JpaRepository<DataSourceEntity, DataSourceKey>