package yuva.assignment.wiproapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Facts(

        @SerializedName("title")
        @Expose
        var title: String? = null,
        @SerializedName("description")
        @Expose
        var description: String? = null,
        @SerializedName("imageHref")
        @Expose
        var imageHref: String? = null

)
