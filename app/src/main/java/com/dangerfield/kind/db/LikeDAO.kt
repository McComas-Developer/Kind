package com.dangerfield.kind.db

import androidx.room.*
import com.dangerfield.kind.model.LikeID
import java.util.*

@Dao
interface LikeDAO {

    /**
     * returns all LikeIDs in table
     */
    @Query("SELECT * from likeIDTable")
    fun getAll(): List<String>


    /**
     * queries for a specific like
     */
    @Query("SELECT * from likeIDTable WHERE id = :withUUID")
    fun queryPost(withUUID: String): List<String>

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