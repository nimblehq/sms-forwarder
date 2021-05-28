package co.nimblehq.smsforwarder.domain.persistence

import co.nimblehq.smsforwarder.domain.data.FilterSmsEntity

interface FilteringSmsPersistence {

    val filters: List<FilterSmsEntity>

    fun addFilter(filter: FilterSmsEntity)
}
