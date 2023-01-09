package myapplication.second.workinghourmanagement

import com.google.gson.annotations.SerializedName

class PostResult {
    @SerializedName("userId")
    private val userId = 0

    @SerializedName("id")
    private val id = 0

    private val title: String? = null

    @SerializedName("body")
    private val bodyValue: String? = null

    override fun toString(): String {
        return "PostResult{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", bodyValue='" + bodyValue + '\'' +
                '}'
    }
}