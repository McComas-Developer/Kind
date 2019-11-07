package com.dangerfield.kind.ui.find;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.dangerfield.kind.api.Repository;
import com.dangerfield.kind.api.Resource;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class FindViewModel extends ViewModel {

    Repository repository = new Repository(FirebaseFirestore.getInstance());

    MutableLiveData<Resource<List<PopularCategory>>> popularCategories;

    public LiveData<Resource<List<PopularCategory>>> getPopularCategories() {
        if(popularCategories == null) {
            popularCategories = repository.getPopularCategories();
        }
        return popularCategories;
    }
}
