# Call Log ✆ ☎ ☏

## About
This is a demo application built using "Kotlin, MVVM, Hilt, Coroutines and Room DB"

- This code helps you to understand how to read the contact from user mobile after getting READ_CALL_LOG permission success.
- The call log may be huge if the user has not cleared the call log for a long period, so it is advisable to read the logs in the background thread. Yes, I'm talking about coroutines.
- Once after reading the log using a cursor, store it in Room DB. 
- Once the reading log from the device and storing it in Room was a success, populate it to the app UI.  

## Built With 🛠🛠🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-jetpack) - Dependency injection library for Android.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Coroutines](https://developer.android.com/kotlin/coroutines) - A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously

**Contributed By:** [Rajkumar Rajan](https://github.com/RajKumar23)

## Project structure
This is the project structure using [***HILT (Dependency injection)***](https://developer.android.com/training/dependency-injection/hilt-jetpack) library.

![](https://github.com/RajKumar23/Call-Log/blob/master/app/Project%20structure.PNG)

## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

## Contact
Wishing to establish the connection with me?

Visit:- [Rajkumar Rajan](https://www.linkedin.com/in/rajkumar-rajan-94463a85/)


