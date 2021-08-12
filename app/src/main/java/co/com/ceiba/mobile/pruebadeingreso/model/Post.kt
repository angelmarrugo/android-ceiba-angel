package co.com.ceiba.mobile.pruebadeingreso.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.SerializedName

@Entity(
    primaryKeys = ["id"],
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onUpdate = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class Post(
    @field:SerializedName("userId")
    val userId: Int,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("body")
    val body: String,
) {
}