package kr.jyh.jumper.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [JumpRoomEntity::class], version = 1)
abstract class JumpRoomDatabase: RoomDatabase() {
    abstract fun JumpRoomDao(): JumpRoomDao

    companion object {
        private var instance: JumpRoomDatabase? = null

        @Synchronized
        fun getInstance(context: Context): JumpRoomDatabase? {
            if (instance == null)
                synchronized(JumpRoomDatabase::class.java){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        JumpRoomDatabase::class.java,
                        "jump.db"
                    ).build()
                }
            return instance
        }
        fun destroyInstance() {
            instance = null
        }
    }
}