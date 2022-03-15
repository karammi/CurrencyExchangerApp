package com.paysera.currencyexchangerapp.data.dataSource.util

import com.paysera.currencyexchangerapp.data.dataSource.remote.RateModel
import com.paysera.currencyexchangerapp.domain.entity.Rates
import javax.inject.Inject

class RateResponseMapper @Inject constructor() : ApiResponseMapper<RateModel, Rates> {
    override fun mapFromModel(model: RateModel): Rates {
        return with(model) {
            Rates(success, timeStamp, base, date, rates)
        }
    }

    override fun mapToModel(entity: Rates): RateModel {
        return with(entity) {
            RateModel(success, timeStamp, base, date, rates)
        }
    }
}
