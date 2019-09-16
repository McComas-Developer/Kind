package com.dangerfield.kind.di

import com.dangerfield.kind.api.Repository
import com.dangerfield.kind.feed.FeedViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    // singleton of the Feed repository
    single { Repository(FirebaseFirestore.getInstance() )}

    // create the viewModel with the repository dependency INJECTED :)
    viewModel { FeedViewModel(get())}
}