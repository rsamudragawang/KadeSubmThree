package com.ganargatul.submthree.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DbHelper(ctx : Context) : ManagedSQLiteOpenHelper(ctx,"FavEvent.db",null,1) {
    companion object {
        private var Instance: DbHelper? = null
        fun getInstance(ctx: Context): DbHelper {
            if (Instance == null) {
                Instance = DbHelper(ctx.applicationContext)
            }
            return Instance as DbHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            FavoriteItems.TABLE_FAVORITE, true,
            FavoriteItems.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteItems.EVENT_ID to INTEGER + UNIQUE,
            FavoriteItems.TEAM_HOME_ID to INTEGER + UNIQUE,
            FavoriteItems.TEAM_AWAY_ID to INTEGER + UNIQUE,
            FavoriteItems.EVENT_NAME to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.dropTable(FavoriteItems.TABLE_FAVORITE, true)
    }
}
val Context.database: DbHelper get() = DbHelper.getInstance(applicationContext)
