# Sports News & YouTube Playlist Manager

## Project Features
- Real-time sports news feed consumption
- YouTube video bookmarking and playlist management
- User authentication with persistent local storage
- Offline-first architecture with Room Database
- Swipe-to-delete gesture support for bookmarks
- Cross-app content sharing via Android Intents
- Asynchronous database operations with UI thread safety

## Technology Stack
- Android (Java)
- Room Database for local persistence
- RecyclerView for efficient list management
- ItemTouchHelper for gesture-based interactions
- Android Intent system for inter-app communication
- Multi-threaded architecture for responsive UI

## Architecture
- Single-Activity pattern with Fragment-based UI
- Room Database with compile-time safety
- Thread-safe Singleton pattern for database access
- Asynchronous I/O pattern for background operations
- Repository pattern for data management
- LiveData and ViewModel for UI updates

## Core Components

### Data Layer (AppDatabase.java)
Three main Room Entities:
- **User**: Handles authentication and serves as foreign key for data privacy
- **BookmarkedNews**: Stores user-specific bookmarked news articles
- **VideoItem**: Manages saved YouTube videos and playlists

### Persistence Strategy (UserDao.java, VideoDao.java)
- Room Database for compile-time safety and abstraction
- Full offline functionality once data is cached
- Private database instance with thread-safe Singleton access

### Threading & UX (HomeFragment.java, NewsDetailFragment.java, SportsFeedFragment.java)
- Background thread handling for all database operations
- `runOnUiThread` for safe UI layer updates
- RecyclerView with ViewHolder recycling for optimal performance
- Asynchronous bookmarking and video saving

### Features & Interactions
- **NewsDetailFragment.java**: Share functionality using Android Intent system
- **PlaylistFragment.java**: Custom ItemTouchHelper for swipe-to-delete gestures
- **NewsGridAdapter.java**: Efficient list rendering with RecyclerView

## Getting Started
1. Clone the repository: `git clone https://github.com/matthewJabbott/SIT305_5.1C.git`
2. Open the project in Android Studio.
3. Ensure you have the required Android SDK and dependencies.
4. Build and run the app on an emulator or physical device.

## Project Structure
├── app

│ ├── src

│ │ ├── main

│ │ │ ├── java

│ │ │ │ └── com.example.sportsnews

│ │ │ │ ├── data

│ │ │ │ │ ├── AppDatabase.java

│ │ │ │ │ ├── dao

│ │ │ │ │ │ ├── UserDao.java

│ │ │ │ │ │ └── VideoDao.java

│ │ │ │ │ └── entity

│ │ │ │ │ ├── User.java

│ │ │ │ │ ├── BookmarkedNews.java

│ │ │ │ │ └── VideoItem.java

│ │ │ │ ├── ui

│ │ │ │ │ ├── MainActivity.java

│ │ │ │ │ ├── fragment

│ │ │ │ │ │ ├── HomeFragment.java

│ │ │ │ │ │ ├── SportsFeedFragment.java

│ │ │ │ │ │ ├── NewsDetailFragment.java

│ │ │ │ │ └── PlaylistFragment.java

│ │ │ │ │ ├── adapter

│ │ │ │ │ │ └── NewsGridAdapter.java

│ │ │ │ │ └── layout

│ │ │ │ │ └── fragment_home.xml

│ │ │ │ └── viewmodel

│ │ │ │ └── NewsViewModel.java

│ │ │ ├── res

│ │ │ └── AndroidManifest.xml

│ │ ├── test

│ │ │ └── java

│ │ │ └── com.example.sportsnews

│ │ │ ├── data

│ │ │ └── ui

│ │ └── androidTest

│ │ └── java

│ │ └── com.example.sportsnews

│ │ └── ui

│ └── build.gradle

├── gradle

└── build.gradle


## Key Implementation Details

### Data Privacy & Encapsulation
- User authentication ensures each user's bookmarks are isolated
- Foreign key relationships between User and BookmarkedNews/VideoItem
- Private database instance prevents resource leaks

### Performance Optimization
- RecyclerView with ViewHolder recycling reduces memory footprint
- Asynchronous Room operations prevent UI thread blocking
- Efficient background threading for disk writes

### User Experience
- Toast notifications for bookmark confirmations
- Intuitive swipe-to-delete for bookmark management
- Cross-app content sharing for seamless integration
- Offline-first approach ensures functionality without internet

## Testing
- Unit tests for ViewModel and Repository classes using JUnit
- UI tests using Espresso
- Instructions for running tests:
  1. Open the project in Android Studio
  2. Ensure your device or emulator is configured
  3. Run tests from the Run menu or using Gradle commands

## Conclusion
This project demonstrates a complete Android application lifecycle: secure user authentication, complex multi-threaded data handling, 
professional list management with gesture support, and system-level inter-app communication. The codebase is architecturally sound and built for scalability.
