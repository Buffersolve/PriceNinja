package ua.priceninja.data.parser

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jsoup.Jsoup
import org.koin.java.KoinJavaComponent.inject
import ua.priceninja.data.db.ItemsTable
import ua.priceninja.data.network.client.NetworkService
import ua.priceninja.utils.Links
import ua.priceninja.utils.Shop

class GetAtbData {

    private val networkService: NetworkService by inject(NetworkService::class.java)
    private val database: Database by inject(Database::class.java)

    suspend fun fetchDataParser() {
        val html = networkService.fetchData(Links.ATB_LINK).trimIndent()
        val doc = Jsoup.parse(html)

        val productNames = doc.select(".catalog-item__title a").map { it.text() }
        val productImages = doc.select(".catalog-item__photo-link img").map { it.attr("src") }
        val salePrices = doc.select(".product-price__top").mapNotNull {
            it.attr("value").takeIf { value -> value.isNotBlank() }
        }
        val regularPrices = doc.select(".product-price__bottom").map { it.text() }

        productNames.forEachIndexed { index, s ->
            transaction(db = database) {
                ItemsTable.insert {
                    it[shop] = Shop.ATB.name
                    it[name] = s
                    it[image] = productImages[index]
                    it[discountPrice] = salePrices[index].toDouble()
                    it[oldPrice] = regularPrices[index].toDouble()
                }
            }
        }


    }

}
