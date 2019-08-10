# PocketWorks Weather App

# **Project features**

- Project allows users to search and specify favorite city/location
- Project displays weather data for this list
- [In progress] Project can also request forecasted wind data
- All data are provided by the open weather API
- Good UX is to be provided

# **About the project**

- Programming Language used: Kotlin
- Platform: Android (24 - 28)
- General architecture: MVVM + Android Architecture Components [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- The project source control follow [GitFlow](https://datasift.github.io/gitflow/IntroducingGitFlow.html) branching model
- Dependency Manager: Gradle

# **To build the project**

- Open and run project with Android Studio

# Implementation:

## Design:

- The app's design takes inspiration from Apple's weather app

## Features (apart from what's required):

- Local storage with Room for faster data load.
- Async using coroutines with scope and lifecycle awareness.
- UI Data binding.
- Swipe to delete.
- Dependencies Injection with Dagger.
- Multiple Unit System in app (metric/imperial).
- Location local time (that sync with device time as well).
- Weather forecast with images.
- Dynamic Weather description.

## Implementation Highlights:

- Use of kotlin new features such as Result class, Enum class, class extensions for faster and better syntax.
- Custom databinding adapters.
- Life cycle awareness coroutines with the new liveData { emit() } syntax.
- Databinding with ObservableFields.
- Room implementation with DAOs and dependency injections.
- Custom JSON adapter for mochi with date formats.
- Design is adapted to Android, but still share familiarity with the iOS version.

## Features that could be done if had more time:

- Transition between the list screen and the details screen.

## Dependencies used:

- [AndroidX + android architecture components](https://developer.android.com/topic/libraries/architecture): Newest features and standards like ViewModel, LiveData, Room are implemented based on Google's documents + sample apps.
- [Dagger](https://github.com/google/dagger): Used for Dependency Injections
- [Retrofit](https://square.github.io/retrofit/): Used for network layer
- [Glide](https://github.com/bumptech/glide): Used for fast image loading
