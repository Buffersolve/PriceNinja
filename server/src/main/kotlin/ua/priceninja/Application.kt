package ua.priceninja

import SERVER_PORT
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.java.KoinJavaComponent.inject
import db.ItemDAO
import db.ItemsTable
import ua.priceninja.data.network.GetSilpoData
import ua.priceninja.data.parser.GetAtbData
import di.startKoinApp
import domain.model.Item
import utils.Shop

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    startKoinApp()

    val database: Database by inject(Database::class.java)
    val itemDAO: ItemDAO by inject(ItemDAO::class.java)

    // Data
    var allData = emptyList<Item>()
    var silpoData = emptyList<Item>()
    var atbData = emptyList<Item>()

    transaction(db = database) {
        SchemaUtils.create(ItemsTable)

        // Silpo
        runBlocking(Dispatchers.IO) {
            GetSilpoData().fetchDataFromServer()
        }
        silpoData = itemDAO.getSilpoItems()

        // ATB
        runBlocking(Dispatchers.IO) {
            GetAtbData().fetchDataParser()
        }
        atbData = itemDAO.getAtbItems()

        // ALL
        allData = itemDAO.getAllItems()
    }

    install(ContentNegotiation) {
        json()
    }
    routing {
        get("/shops") {
            val shopList = Shop.entries.map { it.name }
            call.respond(shopList)
        }
        get("/data") {
            call.respond(allData)
        }
        get("/data/silpo") {
            call.respond(silpoData)
        }
        get("/data/atb") {
            call.respond(atbData)
        }
    }

}

//@Serializable
//data class Data(val shop: String)