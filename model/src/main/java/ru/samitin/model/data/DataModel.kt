package ru.samitin.model.data

import com.google.gson.annotations.SerializedName
import ru.samitin.mytranslater.model.data.Meanings


class DataModel(
    @field:SerializedName("text") val text: String?,
    @field:SerializedName("meanings") val meanings: List<Meanings>?
)
