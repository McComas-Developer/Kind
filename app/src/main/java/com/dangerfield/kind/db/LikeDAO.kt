package com.dangerfield.kind.db

import androidx.room.*
import com.dangerfield.kind.model.LikeID

@Dao
interface LikeDAO {

    /**
     * returns all LikeIDs in table
     */
    @Query("SELECT * from likeIDTable")
    fun getAll(): List<String>

    /**
     *
     * inserts passed likeID into database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(likeID: LikeID)

    /**
     *
     * deletes passed likeID from database
     */
    @Delete
    fun delete(likeID: LikeID)
}