package com.mibaldi.presentation.application

import android.app.Application
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mibaldi.data.datasource.BreweryDataSource
import com.mibaldi.data.datasource.FirestoreDataSource
import com.mibaldi.data.datasource.LoginDataSource
import com.mibaldi.data.repository.BrewerySimpleRepository
import com.mibaldi.data.repository.FirestoreSimpleRepository
import com.mibaldi.data.repository.LoginRepositoryImpl
import com.mibaldi.domain.interactors.account.CreateAccountInteractor
import com.mibaldi.domain.interactors.account.RemoveAccountInteractor
import com.mibaldi.domain.interactors.bar.GetBarInteractor
import com.mibaldi.domain.interactors.bar.UpdateBarInteractor
import com.mibaldi.domain.interactors.beer.BeerPagedInteractor
import com.mibaldi.domain.interactors.login.SignInInteractor
import com.mibaldi.domain.interactors.login.SignOutInteractor
import com.mibaldi.domain.interactors.user.GetCurrentUserInteractor
import com.mibaldi.domain.repository.BreweryRepository
import com.mibaldi.domain.repository.FirestoreRepository
import com.mibaldi.domain.repository.LoginRepository
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.framework.api.brewery.BreweryApi
import com.mibaldi.presentation.framework.api.client.BreweryApiClient
import com.mibaldi.presentation.framework.api.client.LoggingInterceptor
import com.mibaldi.presentation.framework.datasources.BeerPageDataSource
import com.mibaldi.presentation.framework.datasources.BrewerySimpleDataSource
import com.mibaldi.presentation.framework.datasources.FirestoreSimpleDataSource
import com.mibaldi.presentation.framework.datasources.LoginDataSourceImpl
import com.mibaldi.presentation.ui.common.Navigator
import com.mibaldi.presentation.ui.detail.BarDetailActivity
import com.mibaldi.presentation.ui.detail.BarDetailViewModel
import com.mibaldi.presentation.ui.detail.beer.AddBeerBottomDialogFragment
import com.mibaldi.presentation.ui.detail.beer.AddBeerViewModel
import com.mibaldi.presentation.ui.detail.beer.BeerListActivity
import com.mibaldi.presentation.ui.detail.beer.BeerListViewModel
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
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, networkModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    factory<LoginDataSource> { LoginDataSourceImpl }
    factory<FirestoreDataSource> { FirestoreSimpleDataSource(get()) }
    factory<BreweryDataSource> { BrewerySimpleDataSource(get()) }
    factory { Firebase.firestore }
}

val networkModule = module {
    factory { LoggingInterceptor() }
    factory { BreweryApiClient(get()).generatedApi(BreweryApi::class.java) }
}


private val dataModule = module {
    factory<LoginRepository> { LoginRepositoryImpl(get()) }
    factory<FirestoreRepository> { FirestoreSimpleRepository(get()) }
    factory<BreweryRepository> { BrewerySimpleRepository(get()) }
}

private val scopesModule = module {
    scope(named<EmailPasswordActivity>()) {
        viewModel { (activity: EmailPasswordActivity) ->
            EmailPasswordViewModel(
                get { parametersOf(activity) },
                get(),
                get(),
                get(),
                get()
            )
        }
        scoped { (activity: EmailPasswordActivity) -> Navigator(activity) }
        scoped { SignInInteractor(get()) }
        scoped { GetCurrentUserInteractor(get()) }
        scoped { CreateAccountInteractor(get()) }
        scoped { SignOutInteractor(get()) }
    }
    scope(named<MainActivity>()) {
        viewModel { (activity: MainActivity) ->
            MainViewModel(
                get { parametersOf(activity) },
                get()
            )
        }
        scoped { GetBarInteractor(get()) }
        scoped { (activity: MainActivity) -> Navigator(activity) }
    }
    scope(named<BarDetailActivity>()) {
        viewModel { (activity: BarDetailActivity, barView: BarView) ->
            BarDetailViewModel(get {
                parametersOf(
                    activity
                )
            }, barView)
        }
        scoped { (activity: BarDetailActivity) -> Navigator(activity) }
    }

    scope(named<AddBeerBottomDialogFragment>()) {
        viewModel { (bar: BarView) ->
            AddBeerViewModel(
                bar,
                get()
            )
        }
        scoped { UpdateBarInteractor(get()) }
    }

    scope(named<BeerListActivity>()) {
        viewModel { BeerListViewModel(get()) }
        scoped { BeerPageDataSource(get()) }
        scoped { BeerPagedInteractor(get()) }
    }
    scope(named<ProfileActivity>()) {
        viewModel { (activity: ProfileActivity) ->
            ProfileViewModel(
                get { parametersOf(activity) },
                get(),
                get(),
                get()
            )
        }
        scoped { (activity: ProfileActivity) -> Navigator(activity) }
        scoped { GetCurrentUserInteractor(get()) }
        scoped { SignOutInteractor(get()) }
        scoped { RemoveAccountInteractor(get()) }
    }


}