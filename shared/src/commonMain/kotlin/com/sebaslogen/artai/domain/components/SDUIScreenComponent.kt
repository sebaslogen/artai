package com.sebaslogen.artai.domain.components

import com.arkivanov.decompose.ComponentContext
import com.sebaslogen.artai.domain.models.Url

class SDUIScreenComponent(componentContext: ComponentContext, val url: Url) : ComponentContext by componentContext {

}
