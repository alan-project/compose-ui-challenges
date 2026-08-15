# Level 2 — State and Input Challenges

Level 2 moves from drawing static Compose layouts to coordinating multiple pieces of screen state. Every project is a standalone Android app with a single screen, an MVVM presentation layer, and an in-memory repository.

## Branches

- `exercise` contains the complete project, mock data, repository, theme, and image assets. The screen route and ViewModel are intentionally left as small compile-ready starting points.
- `solution` contains the finished UI, ViewModel, and interactions.

Switching branches changes every challenge in this repository at the same time. Commit or stash your work before switching.

## Projects

### 01 — Shopping Cart

Build a polished cart where users can select products, change quantities, remove and restore an item, and see totals update automatically.

Challenge focus:

- Derived UI state
- List item events
- Quantity boundaries
- One-shot Snackbar events
- Edge-to-edge bottom actions

### 02 — Checkout Form

Build a checkout form with a swipeable hero, validation, shipping and payment choices, a calculated total, and an order confirmation dialog.

Challenge focus:

- Text input and validation
- Pager state and horizontal gestures
- Focus and IME-safe layouts
- Selectable controls
- Submission state
- Form-driven UI state

### 03 — Media Player

Build a dark, image-led music player with playback controls, seeking, repeat and shuffle modes, and a queue bottom sheet.

Challenge focus:

- Time-based state updates
- Slider state
- State-driven controls
- Modal bottom sheets
- Accessibility descriptions

## Shared Constraints

- One screen per project
- MVVM only
- In-memory mock data
- No Hilt, Room, or Navigation
- Real bitmap imagery where imagery is part of the design

## Visual Identities

- Shopping Cart: cobalt blue and lemon yellow on cool white
- Checkout Form: neutral form surfaces with bold violet, cyan, and vivid yellow hero slides
- Media Player: near-black with acid lime, electric cyan, and a pink favorite accent
