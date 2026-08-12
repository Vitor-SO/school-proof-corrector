package vitorcode.school_proof_corrector.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vitorcode.school_proof_corrector.data.repositories.SignInRepositoryImpl
import vitorcode.school_proof_corrector.data.repositories.ILoginRepository
import vitorcode.school_proof_corrector.data.repositories.ISignInRepository
import vitorcode.school_proof_corrector.data.repositories.LoginRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AppRepositoriesModule {
    @Binds
    abstract fun bindLoginRepository(
        loginRepository: LoginRepositoryImpl
    ): ILoginRepository

    @Binds
    abstract fun bindSignInRepository(
        signInRepositoryImpl: SignInRepositoryImpl
    ): ISignInRepository

//    @Binds
//    abstract fun bindTeacherRepository(
//        teacherRepositoryImpl: TeacherRepositoryImpl
//    ): ITeacherRepository

}
