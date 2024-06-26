package ua.priceninja.data.parser

import client.NetworkService
import db.ItemsTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.koin.java.KoinJavaComponent.inject
import utils.Links
import utils.Shop

class GetBlyzenkoData {

    private val networkService: NetworkService by inject(NetworkService::class.java)
    private val database: Database by inject(Database::class.java)

    fun fetchDataParser() {
        runBlocking(Dispatchers.IO) {
            val html = networkService.fetchData(Links.BLYZENKO_LINK).trimIndent()
            val doc = Jsoup.parse(html)

            val productNames = doc.select(".sale").map { it.select("span")[1].text().orEmpty() }

            val productImages: List<String> =
                doc.select(".sale img, .sale source").mapNotNull { element ->
                    val srcset = element.attr("srcset").split(",").firstOrNull()
                    srcset?.substringBefore(" ") ?: element.attr("src")
                }

            val salePrices = doc.select(".sale .price span:nth-of-type(2)").mapNotNull {
                it.text().takeIf { value -> value.isNotBlank() }
            }
            val regularPrices = doc.select(".sale .price span:nth-of-type(1)").map { it.text() }

            productNames.forEachIndexed { index, s ->
                transaction(db = database) {
                    ItemsTable.insert {
                        it[shop] = Shop.BLYZENKO.name
                        it[name] = s
                        it[image] = productImages[index]
                        it[discountPrice] = salePrices[index].toDouble()
                        it[oldPrice] = regularPrices[index].toDouble()
                    }
                }
            }
        }
    }

}