package com.clo.accloss.session.domain.mappers

import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.Session as SessionEntity

fun SessionEntity.toDomain(): Session = Session(
    user = user,
    empresa = empresa,
    active = active
)

fun Session.toDatabase(): SessionEntity = SessionEntity(
    user = user,
    empresa = empresa,
    active = active
)
