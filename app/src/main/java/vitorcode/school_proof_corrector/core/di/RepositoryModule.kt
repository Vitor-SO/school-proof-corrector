package vitorcode.school_proof_corrector.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vitorcode.school_proof_corrector.data.repositories.BulletinRepositoryImpl
import vitorcode.school_proof_corrector.data.repositories.IBulletinRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class AppRepositoriesModule {
    @Binds
    abstract fun bindBulletinRepository(
        bulletinRepositoryImpl: BulletinRepositoryImpl
    ): IBulletinRepository
}
