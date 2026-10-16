# ConflictGlobe

ConflictGlobe is an Android application written in Kotlin for recording and tracking conflict information.

The application allows users to create, view, edit, delete, search and filter conflict records while storing data locally using JSON persistence.

## Features

- Add new conflict records
- View all conflicts using a RecyclerView
- View full conflict details
- Edit existing conflict records
- Delete conflicts with confirmation
- Search conflicts by name or region
- Filter conflicts by status
- Select conflict start dates using a DatePicker
- Select status and severity using dropdown controls
- Store latitude and longitude coordinates
- Validate required fields and coordinate ranges
- Store records locally using JSON
- Use Up/Back navigation between screens
- Automated unit testing for model validation
- Material-based interface styling

## Conflict Data

Each conflict record contains:

- Name
- Region
- Start date
- Status
- Severity
- Latitude
- Longitude
- Description

## Validation

Conflict records are validated before being saved.

The application checks that:

- Conflict name is not blank
- Region is not blank
- Latitude is between -90 and 90
- Longitude is between -180 and 180

## Persistence

ConflictGlobe stores conflict records in a local JSON file named:

```text
conflicts.json
```

The file is stored using Android internal application storage.

Records are loaded when the application is opened and saved after changes are made.

## Search and Filtering

The main conflict list supports:

- Case-insensitive search by conflict name
- Case-insensitive search by region
- Filtering by conflict status
- Combined search and status filtering

## Testing

Unit tests are included for the `Conflict` model.

Tests cover:

- Valid conflict records
- Blank names
- Blank regions
- Invalid latitude values
- Invalid longitude values
- Valid latitude boundary values
- Valid longitude boundary values

Tests can be run using Android Studio or Gradle.

### macOS / Linux

```bash
./gradlew test
```

### Windows

```bash
gradlew.bat test
```

## Running the Project

1. Clone or download the repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync.
4. Select an Android emulator or connected Android device.
5. Run the application.

## Project Structure

Main Kotlin classes:

```text
Conflict.kt
ConflictStore.kt
ConflictAdapter.kt
MainActivity.kt
AddEditConflictActivity.kt
ConflictDetailActivity.kt
```

Main layouts:

```text
activity_main.xml
activity_add_edit.xml
activity_detail.xml
item_conflict.xml
```

Tests:

```text
ConflictTest.kt
```

## Repository Workflow

Development uses:

```text
main
develop
feature/*
test/*
docs/*
```

Feature work is tracked using GitHub Issues and developed on separate branches before being merged into `develop`.

Stable work is merged from `develop` into `main`.

## Repository Recovery

The original project repository was accidentally deleted during development.

This repository was recreated from the recovered project files. The current Git history records the reconstruction and subsequent development work.

## Technologies

- Kotlin
- Android SDK
- AndroidX
- RecyclerView
- Material Components
- JSON
- JUnit
- Git
- GitHub
