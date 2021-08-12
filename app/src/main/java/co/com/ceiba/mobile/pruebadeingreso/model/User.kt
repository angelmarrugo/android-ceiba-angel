package co.com.ceiba.mobile.pruebadeingreso.model

import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(
    indices = [
        Index("id")],
        primaryKeys = ["id"]
)
data class User(
    @field:SerializedName("id")
    val id: Int?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("email")
    val email: String?,
    @field:SerializedName("phone")
    val phone: String?
)


