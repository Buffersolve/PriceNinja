package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SilpoResponseModel(
    val limit: Int,
    val offset: Int,
    val total: Int,
    val items: List<Item>
)

//@Serializable
//data class SilpoItems(
//    val id: String,
//    val title: String,
//    val icon: String,
//    val price: Double,
//    val oldPrice: Double?,
//    val offerId: String,
//    val ratio: String,
//    val companyId: String,
//    val createdAt: String,
//    val slug: String,
//    val addToBasketStep: Double,
//    val stock: Double,
//    val displayPrice: Double,
//    val displayOldPrice: Double?,
//    val displayRatio: String,
////    val guestProductRating: Double?, // Change the type to Int if necessary
////    val guestProductRatingCount: Int?, // Change the type to Double if necessary
//    val brandId: String?,
//    val brandTitle: String?
//)