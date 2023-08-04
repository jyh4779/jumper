package kr.jyh.jumper.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TB_JUMPER_SCORE")
data class JumpRoomEntity(
    @PrimaryKey
    val playDate: String,
    val sPlayerName: String = "name",
    val score: Int
)
