package session.di

import org.koin.dsl.module
import com.clo.accloss.session.data.SessionLocalDataSource
import com.clo.accloss.session.domain.repository.SessionRepository

val sessionModule = module {
    single {
        SessionLocalDataSource(get(), get())
    }

    single {
        SessionRepository(get())
    }
}
