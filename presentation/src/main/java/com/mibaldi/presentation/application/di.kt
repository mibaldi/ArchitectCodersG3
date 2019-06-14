package com.mibaldi.presentation.application

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.mibaldi.data.datasource.FirestoreDataSource
import com.mibaldi.data.datasource.LoginDataSource
import com.mibaldi.data.repository.FirestoreSimpleRepository
import com.mibaldi.data.repository.LoginRepositoryImpl
import com.mibaldi.domain.interactors.account.CreateAccountInteractor
import com.mibaldi.domain.interactors.account.RemoveAccountInteractor
import com.mibaldi.domain.interactors.bar.GetBarInteractor
import com.mibaldi.domain.interactors.login.SignInInteractor
import com.mibaldi.domain.interactors.login.SignOutInteractor
import com.mibaldi.domain.interactors.user.GetCurrentUserInteractor
import com.mibaldi.domain.repository.FirestoreRepository
import com.mibaldi.domain.repository.LoginRepository
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.framework.datasources.FirestoreSimpleDataSource
import com.mibaldi.presentation.framework.datasources.LoginDataSourceImpl
import com.mibaldi.presentation.ui.detail.BarDetailActivity
import com.mibaldi.presentation.ui.detail.BarDetailViewModel
import com.mibaldi.presentation.ui.login.EmailPasswordActivity
import com.mibaldi.presentation.ui.login.EmailPasswordViewModel
import com.mibaldi.presentation.ui.main.MainActivity
import com.mibaldi.presentation.ui.main.MainViewModel
import com.mibaldi.presentation.ui.profile.ProfileActivity
import com.mibaldi.presentation.ui.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI(){
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    factory<LoginDataSource> { LoginDataSourceImpl }
    factory<FirestoreDataSource> { FirestoreSimpleDataSource(get()) }
    factory { FirebaseFirestore.getInstance() }
}

private val dataModule = module {
    factory<LoginRepository> { LoginRepositoryImpl(get())  }
    factory<FirestoreRepository> { FirestoreSimpleRepository(get()) }
}

private val scopesModule = module {
    scope(named<EmailPasswordActivity>()) {
        viewModel { EmailPasswordViewModel(get(),get(),get(),get()) }
        scoped { SignInInteractor(get())  }
        scoped { GetCurrentUserInteractor(get())  }
        scoped { CreateAccountInteractor(get())  }
        scoped { SignOutInteractor(get())  }
    }
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get()) }
        scoped { GetBarInteractor(get()) }
    }
    scope(named<BarDetailActivity>()) {
        viewModel { (barView: BarView) -> BarDetailViewModel(barView) }
    }
    scope(named<ProfileActivity>()) {
        viewModel { ProfileViewModel(get(),get(),get()) }
        scoped { GetCurrentUserInteractor(get())  }
        scoped { SignOutInteractor(get())  }
        scoped { RemoveAccountInteractor(get())  }
    }
}