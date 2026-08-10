package vitorcode.school_proof_corrector.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vitorcode.school_proof_corrector.data.repositories.AuthRepositoryImpl
import vitorcode.school_proof_corrector.data.repositories.IAuthRepository
import vitorcode.school_proof_corrector.data.repositories.IPeriodRepository
import vitorcode.school_proof_corrector.data.repositories.ISignInRepository
import vitorcode.school_proof_corrector.data.repositories.IStudentRepository
import vitorcode.school_proof_corrector.data.repositories.ISubjectRepository
import vitorcode.school_proof_corrector.data.repositories.ITeacherRepository
import vitorcode.school_proof_corrector.data.repositories.PeriodRepositoryImpl
import vitorcode.school_proof_corrector.data.repositories.SignInRepositoryImpl
import vitorcode.school_proof_corrector.data.repositories.StudentRepositoryImpl
import vitorcode.school_proof_corrector.data.repositories.SubjectRepositoryImpl
import vitorcode.school_proof_corrector.data.repositories.TeacherRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AppRepositoriesModule {
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): IAuthRepository

    @Binds
    abstract fun bindSignInRepository(
        signInRepositoryImpl: SignInRepositoryImpl
    ): ISignInRepository

    @Binds
    abstract fun bindStudentRepository(
        studentRepositoryImpl: StudentRepositoryImpl
    ): IStudentRepository

    @Binds
    abstract fun bindSubjectRepository(
        subjectRepositoryImpl: SubjectRepositoryImpl
    ): ISubjectRepository

    @Binds
    abstract fun bindTeacherRepository(
        teacherRepositoryImpl: TeacherRepositoryImpl
    ): ITeacherRepository

    @Binds
    abstract fun bindPeriodRepository(
        periodRepositoryImpl: PeriodRepositoryImpl
    ): IPeriodRepository
}
