package di

import db.DatabaseSettings
import db.ItemDAO
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

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