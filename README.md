# Compose Practice Lab

A collection of focused, single-screen Android projects for practicing Jetpack Compose. Every project uses English UI, MVVM, in-memory mock data, and no dependency injection, database, or navigation framework.

## How to Use the Repository

This repository uses two branches for every project:

- `exercise`: complete project setup, models, mock data, repositories, themes, and image assets with compile-ready UI and ViewModel starting points.
- `solution`: the finished implementation and its tests.

Because the branches belong to the whole repository, switching from `exercise` to `solution` changes every project at once. Commit or stash your work before switching branches.

## Levels

### [Level 1 — Foundation](level-01-foundation)

Six projects covering lists, grids, product details, settings controls, search and filtering, and dashboard composition.

### [Level 2 — State and Input](level-02-state-and-input)

Three projects covering derived state and one-shot events, validated text input, and time-based media controls.

## Shared Project Rules

- One independent Android project per folder
- One screen per project
- Jetpack Compose with Material 3
- MVVM and unidirectional data flow
- In-memory repositories and mock server-style data
- Real bitmap images when the design calls for photography
- No Hilt, Room, or Navigation
