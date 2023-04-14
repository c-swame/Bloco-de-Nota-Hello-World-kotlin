package com.example.helloworld

import java.util.*

class CardElementModel(titleParam: String, descriptionParam: String?) {
    var title: String = ""
    var description: String? = ""

    init {
        title = titleParam.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        description = descriptionParam
    }
}