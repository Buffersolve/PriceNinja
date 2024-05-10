package ua.priceninja.data.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ua.priceninja.data.db.ItemsTable.shop
import ua.priceninja.domain.model.Item
import ua.priceninja.utils.Shop

object ItemsTable : IntIdTable() {
    val shop = varchar("shop", 50).default("Silpo")
    val name = varchar("title", 255)
    val image = varchar("icon", 255)
    val discountPrice = double("price")
    val oldPrice = double("oldPrice").nullable()
}

fun ResultRow.toItem(): Item {
    return Item(
        shop = this[shop],
        name = this[ItemsTable.name],
        image = this[ItemsTable.image],
        discountPrice = this[ItemsTable.discountPrice],
        oldPrice = this[ItemsTable.oldPrice]
    )
}

class ItemDAO {

    fun getSilpoItems(): List<Item> {
        return transaction {
            ItemsTable.selectAll().where { shop eq Shop.SILPO.name }
                .map { it.toItem() }
        }
    }

    fun getAtbItems(): List<Item> {
        return transaction {
            ItemsTable.selectAll().where { shop eq Shop.ATB.name }
                .map { it.toItem() }
        }
    }

    fun getAllItems(): List<Item> {
        return transaction {
            ItemsTable.selectAll().map { it.toItem() }
        }
    }
}
