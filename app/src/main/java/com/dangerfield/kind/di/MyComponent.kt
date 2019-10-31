package com.dangerfield.kind.di

import com.dangerfield.kind.MainActivity
import com.dangerfield.kind.ui.MainFragment
import com.dangerfield.kind.ui.feed.FeedFragment
import com.dangerfield.kind.ui.find.FindFragment
import dagger.Component
import javax.inject.Singleton

// Programmer: Michael R. McComas
// ** interface for the FeedModule object ** //

@Singleton
@Component(modules = [MyModule::class])

interface MyComponent{

    fun inject(mainActivity : MainActivity)         // Inject method for Main Activity
    //fun injectMainFrag(mainFragment: MainFragment)          // Inject method for Main Fragment
    //fun injectFeed(mainFeedFragment: FeedFragment)      // Inject method for Feed Fragment
    //fun injectFindFrag(mainFind : FindFragment)             // Inject method for Find Fragment

}