package kr.jyh.jumper.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface JumpRoomDao {
    @Query("SELECT sPlayerName FROM TB_JUMPER_SCORE ORDER BY playDate DESC")
    fun getName(): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScore(entity: JumpRoomEntity)

    @Query("SELECT sPlayerName, score, playDate FROM TB_JUMPER_SCORE ORDER BY score DESC LIMIT 10 OFFSET 0")
    fun getScore(): Array<JumpRoomEntity>
}