package co.nimblehq.smsforwarder

import co.nimblehq.smsforwarder.domain.data.FilterSmsEntity
import co.nimblehq.smsforwarder.domain.persistence.FilteringSmsPersistence
import javax.inject.Inject

// TODO Update to use database
class FilteringSmsPersistenceImpl @Inject constructor() : FilteringSmsPersistence {

    private val _filters = mutableListOf<FilterSmsEntity>()

    override val filters: List<FilterSmsEntity>
        get() = _filters

    override fun addFilter(filter: FilterSmsEntity) {
        _filters.add(filter)
    }
}
