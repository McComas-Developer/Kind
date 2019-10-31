package com.dangerfield.kind.di

import com.dangerfield.kind.api.Repository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
// Programmer: Michael R. McComas

@Module
// ** Module for containing a single instance of the Feed view object ** //

class MyModule{
    @Provides
    @Singleton
    fun inject() : FirebaseFirestore{
        var database : FirebaseFirestore
        database = FirebaseFirestore.getInstance()
        return database
    }
}