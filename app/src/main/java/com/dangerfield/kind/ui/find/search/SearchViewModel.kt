package com.dangerfield.kind.ui.find.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dangerfield.kind.api.KindRepository
import com.dangerfield.kind.api.Repository
import com.google.firebase.firestore.FirebaseFirestore

class SearchViewModel : ViewModel() {

    private var repository: Repository = Repository(FirebaseFirestore.getInstance())

    var currentSearchTerm = MutableLiveData<String>()

    var searchResult = Transformations.switchMap(currentSearchTerm) {
        repository.getPostsWithTag(it)
    }

}