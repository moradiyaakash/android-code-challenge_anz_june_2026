# Android Code Challenge – ANZ June 2026

## Overview

This Android application was developed as part of a technical
assessment. It demonstrates modern Android development practices using
Kotlin, Jetpack Compose, and Clean Architecture.

The app fetches and displays a list of users from a remote API while
handling loading, error, and empty states.

------------------------------------------------------------------------

## Tech Stack

-   Kotlin
-   Jetpack Compose (Material 3)
-   MVVM + Clean Architecture
-   Hilt (Dependency Injection)
-   Retrofit (Networking)
-   Coroutines & StateFlow
-   Coil (Image Loading)
-   JUnit & MockK (Unit Testing)

------------------------------------------------------------------------

## Architecture

The project follows Clean Architecture principles with clear separation
between:

-   Presentation Layer -- Compose UI + ViewModel
-   Domain Layer -- UseCases and business logic
-   Data Layer -- Repository and API integration

State is exposed via `StateFlow` and collected using lifecycle-aware
APIs.

------------------------------------------------------------------------

## Key Features

- 📋 Users list fetched from remote API
- ⏳ Loading, error, and empty state handling
- 🔄 Retry functionality on failure
- 🔒 Safe coroutine cancellation to prevent duplicate requests
- ♻️ Lifecycle-aware state collection
- 🚀 Smooth scrolling with stable item keys

------------------------------------------------------------------------

## How to Run

1.  Clone the repository
2.  Open in Android Studio
3.  Ensure JDK 17 is configured
4.  Sync and run on an emulator or device

Minimum SDK: 24

------------------------------------------------------------------------

## Notes

The implementation focuses on clean architecture, maintainability, and
safe concurrency handling within the scope of the assignment.

------------------------------------------------------------------------

## Author

Akash Moradia
Senior Android Developer
