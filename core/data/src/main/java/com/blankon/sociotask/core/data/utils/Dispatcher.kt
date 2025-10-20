package com.blankon.sociotask.core.data.utils

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME


@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val niaDispatcher: SocioDispatchers)

enum class SocioDispatchers {
    Default,
    IO,
}
