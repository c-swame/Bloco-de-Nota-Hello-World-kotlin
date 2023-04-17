package com.example.helloworld

import java.util.*

class CardElementModel(titleParam: String, descriptionParam: String?) {
    var title: String = ""
    var description: String? = ""
    var taskId: UUID;
    // Para a prioridade criar uma lista de opções e usar o
    // var priorityLevel

    init {
        title = titleParam.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        description = descriptionParam
        taskId = UUID.randomUUID();
    }
}