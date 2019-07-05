package yuva.assignment.wiproapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Country(
        @SerializedName("title")
        @Expose
        var title: String? = null,
        @SerializedName("rows")
        @Expose
        var rows: List<Facts>? = null
)
