package ru.samitin.model.dataDto

import com.google.gson.annotations.SerializedName
import ru.samitin.mytranslater.model.data.MeaningsDto


class SearchResultDto(
    @field:SerializedName("text") val text: String?,
    @field:SerializedName("meanings") val meanings: List<MeaningsDto>?
)
