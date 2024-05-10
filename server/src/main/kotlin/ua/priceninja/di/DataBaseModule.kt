package ua.priceninja.di

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module
import ua.priceninja.data.db.DatabaseSettings
import ua.priceninja.data.db.ItemDAO

val databaseModule = module {
    single {
        Database.connect(
            url = DatabaseSettings.url,
            driver = DatabaseSettings.driver,
            user = DatabaseSettings.user,
            password = DatabaseSettings.password
        )
    }
}

val daoModule = module {
    single { ItemDAO() }
}