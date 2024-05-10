package ua.priceninja.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ua.priceninja.utils.Shop

@Serializable
data class Item(
//    @Transient
    val shop: String = Shop.SILPO.name,
    @SerialName("title")
    val name: String,
    @SerialName("icon")
    val image: String,
    @SerialName("price")
    val discountPrice: Double,
    @SerialName("oldPrice")
    val oldPrice: Double?,
)
