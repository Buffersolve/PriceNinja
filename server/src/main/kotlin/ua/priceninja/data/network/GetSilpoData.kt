package ua.priceninja.data.network

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.java.KoinJavaComponent.inject
import utils.Links
import db.ItemsTable
import client.NetworkService
import domain.model.SilpoResponseModel

class GetSilpoData {

    private val networkService: NetworkService by inject(NetworkService::class.java)
    private val database: Database by inject(Database::class.java)

    suspend fun fetchDataFromServer() {
        val jsonString = networkService.fetchData(Links.SILPO_MAIN_FETCH)
        val data = JsonFormatter.format.decodeFromString<SilpoResponseModel>(jsonString)

        data.items.forEach { item ->
            transaction(db = database) {
                ItemsTable.insert {
                    it[shop] = item.shop
                    it[name] = item.name
                    it[image] = Links.SILPO_IMAGE + item.image
                    it[discountPrice] = item.discountPrice
                    it[oldPrice] = item.oldPrice
                }
            }
        }
    }

}