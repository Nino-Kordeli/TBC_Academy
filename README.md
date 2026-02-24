# 📖 Overview

**MyFitnessJourney** is a modern Android fitness and health companion designed to serve as a **complete daily wellness platform**, combining **real-time activity tracking**, **nutrition management**, and **healthy lifestyle discovery** into a single seamless mobile experience.

The application functions as an **all-in-one fitness ecosystem** where users can **track movement, manage nutrition, and discover workouts and recipes** without switching between multiple applications. Built with a **scalable multi-module architecture** and modern Android technologies, MyFitnessJourney emphasizes **performance, reliability, and long-term habit building**.

Rather than relying solely on manual input, the application integrates directly with **device hardware sensors** to automatically monitor physical activity, enabling accurate step tracking and calorie expenditure throughout the day. This real-time monitoring works continuously through a **Foreground Health Service**, ensuring progress tracking remains active even when the application runs in the background.

Alongside activity tracking, MyFitnessJourney provides structured tools for managing nutrition and exercise routines. Users can log meals, monitor caloric intake, and compare consumed calories against energy burned through daily movement and workouts. By combining these metrics into a unified dashboard, the application helps users better understand their health patterns and maintain consistency in their fitness journey.

The platform also includes a **discovery-focused experience** powered by multiple external APIs, allowing users to explore curated workouts and healthy recipes. This transforms the app from a simple tracker into a **lifestyle companion** that actively helps users make healthier decisions by providing actionable inspiration rather than just raw data.

Designed using **enterprise-level Android architecture principles**, MyFitnessJourney demonstrates how modern mobile applications can integrate **background processing, hardware interaction, and modular scalability** while maintaining a clean and maintainable codebase.

# Screenshots
---
## Splash Screen

<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/4ed68a62-be2e-40ff-babb-50a7209cd2f8" />

## Welcome Screen

<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/60bf2585-cba8-4308-990d-f1c8925ae33d" />

## Login Screen

<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/b3794afd-4cb4-418e-8d2c-fa15ae2ee76a" />

## Registration Screen

<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/485d5f70-ed76-4330-8cc1-b73bce8b69b2" />

## Quiz Pages

<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/1a628f92-fb7d-4c1c-97a1-855768075351" />
<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/0758300f-b4c2-4203-99ab-963e644d80a6" />
<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/7ba0f375-8f4e-47cb-8b5f-8731619b54f9" />
<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/494cdea4-637f-4016-be75-33570b1cccfb" />
<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/a6091ed3-21fe-4312-8096-e79bde1e4ffb" />
<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/7f83480d-e29e-400c-9cb1-959240d74daa" />
<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/a7185ae0-1127-4c0d-8a08-624e8f75eb1c" />

## Dashboard Screen

<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/bde3d3e8-fb3b-47e0-a5af-cae1963cf7d0" />

## Exercises Screen
<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/c077699b-01f7-48e2-b3f9-e35be2009536" />

## Recipes Screen

<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/c7cc7c4d-bbf8-4c97-b447-61c8ef926e4c" />

## Diary Screen

<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/393114f5-90e7-4eab-b483-0cbb072cc60c" />

## Add Food Screen
<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/f6a2ca4d-2e53-4764-8784-bca57a7ee114" />

## Food Nutrition Screen
<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/3c5525f6-b571-4f5e-a2d4-f5c680802cb4" />

## Add Food Screen with Logged Data
<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/d9678cea-9709-4a34-9f54-67be2bdde4ba" />

## Exercises Screen Logging
<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/918d74ce-8463-4529-a19a-7d7f053ba93f" />

## Dashboard Screen with Logged Data
<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/68476022-b71c-4c0d-87f8-19e757ef82de" />

## User Profile Screen
<img width="465" height="1023" alt="image" src="https://github.com/user-attachments/assets/b8a15bf2-7b98-4b1f-afc3-8d540572d46c" />


---
# 🛠 Tech Stack

## 🏗 Architecture & Design Patterns

• **Multi-Module Architecture** — Clean Architecture with Feature API / Implementation split enforcing strict module boundaries  
• **Clean Architecture** — Separation of Data, Domain, and Presentation layers  
• **MVI-Inspired State Management** — Reactive UI with predictable state handling  
• **Repository Pattern** — Centralized data access across network and local sources  
• **Unidirectional Data Flow (UDF)** — Immutable UI states powered by Compose  

---

## 🎨 UI & Styling

• **Jetpack Compose** — 100% declarative modern Android UI toolkit  
• **Material Design 3 (M3)** — Modern UI system with Light/Dark theme support  
• **Compose Foundation & Layout APIs** — Custom layouts and advanced UI composition  
• **Navigation Compose** — Type-safe navigation between feature modules  
• **Coil (Compose Image Loader)** — Efficient async image loading for recipes and workouts  
• **Custom Design System** — Modularized typography, themes, and reusable UI components  
• **Splash Screen** — Modern Android startup experience  
• **Adaptive App Icon** — Custom launcher icon supporting all screen densities  

---

## 📡 Networking & API

• **Retrofit 2** — Type-safe HTTP client for API communication  
• **OkHttp3** — Networking interceptors and optimized request handling  
• **Kotlinx Serialization** — Kotlin-native JSON parsing (Gson/Moshi alternative)  
• **Coroutine-based Networking** — Fully asynchronous API calls  
• **Unified Response Wrapper** — Structured network result handling  

Integrated Endpoints:

• **Food API** — Nutrition and calorie data  
• **Recipe API** — Healthy meal discovery  
• **Workout API** — Exercise and fitness content  

---

## 💾 Data Persistence & Storage

• **Jetpack DataStore (Preferences)** — Secure asynchronous storage for sessions and tokens  
• **Firebase Authentication** — Cloud-based user identity management  
• **Persistent Session Management** — Automatic login restoration  

---

## ⚙️ Background & System Integration

• **WorkManager** — Guaranteed background execution (DailyResetWorker)  
• **Foreground Services** — Persistent StepCounterService for continuous tracking  
• **Hardware Sensor Integration** — Real-time step tracking via SensorManager & STEP_COUNTER sensor  
• **Android 14+ Health Service Compliance** — Proper foreground service types  

---

## 💉 Dependency Injection

• **Hilt (Dagger)** — Dependency injection across application layers  
• **Hilt Navigation Compose** — Scoped ViewModel injection  
• **Hilt + WorkManager Integration** — Dependency injection inside background workers  

---

## 🛠 Build System & Engineering Tools

• **Gradle Kotlin DSL** — Type-safe build configuration (`.gradle.kts`)  
• **Convention Plugins (build-logic module)** — Centralized Gradle configuration across modules  
• **Version Catalogs (`libs.versions.toml`)** — Unified dependency management  
• **KSP (Kotlin Symbol Processing)** — Faster annotation processing for Hilt & Serialization  

---

## 🧪 Programming Language

• **Kotlin (100%)** — Entire codebase written in Kotlin  
• **Kotlin Coroutines & Flow** — Asynchronous programming and reactive streams  
• **Sealed Classes & State Models** — Type-safe UI state management

# ⭐ Key Features

## 🏃 Intelligent Fitness Tracking

MyFitnessJourney provides **continuous activity monitoring** through direct integration with Android hardware sensors.

- Real-time step tracking using **STEP_COUNTER sensors**
- Automatic activity monitoring powered by **SensorManager**
- Hardware-level movement detection
- Continuous tracking via a **Foreground Health Service**
- Persistent system notification for reliability
- Tracking continues even when the app is minimized or closed

This approach minimizes battery usage while maximizing tracking accuracy, offering a reliable alternative to manual activity logging.

---

## 🔥 Calorie Awareness & Daily Progress Monitoring

The application helps users maintain a balanced lifestyle by visualizing **energy intake vs. energy expenditure**.

Users can:

- Log consumed foods into a daily diary
- Monitor caloric intake dynamically
- Track calories burned through steps and activity
- View structured daily progress summaries
- Maintain awareness of fitness goals through visual feedback

### 📅 Automated Daily Reset

At midnight:

- Daily step count resets
- Calorie tracking refreshes
- Progress data updates automatically

This system is powered by **WorkManager**, ensuring reliable execution even if the app is not actively running.

---

## 🥗 Nutrition Tracking & Food Logging

MyFitnessJourney includes an integrated nutrition system allowing users to actively manage dietary habits.

**Features include:**

- Food search powered by external nutrition APIs
- Meal logging within a daily diary
- Real-time calorie updates after food entries
- Structured monitoring of eating habits
- Persistent progress storage

This encourages consistent dietary awareness and helps users understand how nutrition impacts fitness goals.

---

## 💪 Workout Discovery

To support structured exercise, the application provides curated workout exploration.

Users can:

- Browse workout recommendations from remote endpoints
- Discover exercises for different fitness levels
- View workout imagery and descriptions
- Use workouts as daily exercise guidance

This feature lowers the barrier for beginners who may not know how to start exercising.

---

## 🍲 Healthy Recipe Exploration

The integrated recipe discovery system promotes healthier eating choices through accessible meal inspiration.

- Healthy recipe recommendations from external APIs
- Visual browsing experience with optimized image loading
- Nutrition-friendly meal exploration
- Lifestyle-focused food discovery

---

## 🔐 Secure Authentication & Persistent Sessions

User identity and data continuity are handled using secure authentication mechanisms.

- Firebase Authentication integration
- Secure login and registration
- Persistent sessions across app restarts
- Token storage using **Jetpack DataStore**
- Automatic session restoration

Users can return to the app without losing progress or re-authenticating repeatedly.

---

## ⚙️ Automated Background Systems

MyFitnessJourney leverages Android background processing for seamless automation.

### 📅 Daily Reset Automation

Every midnight:

- Steps reset
- Calorie tracking resets
- Daily progress refreshes automatically

Handled using **WorkManager** to guarantee execution under system constraints.

### 🚶 Continuous Health Monitoring

A **Foreground Service** maintains uninterrupted communication with hardware sensors, ensuring accurate real-time tracking throughout the day.

---

# 👥 Target Audience

MyFitnessJourney is designed for:

- Individuals beginning their fitness journey
- Users seeking structured calorie and activity tracking
- Health-conscious individuals managing lifestyle balance
- Android developers studying modern scalable architecture

---

# 🎯 Application Vision

MyFitnessJourney goes beyond traditional fitness trackers by combining **automatic activity monitoring**, **nutrition awareness**, and **lifestyle discovery** into a unified platform.

The application focuses not only on tracking progress but also on helping users develop **sustainable habits** through consistent feedback, accessible guidance, and intelligent automation.

By blending modern Android engineering with practical health tools, MyFitnessJourney creates a scalable foundation for future intelligent fitness experiences such as wearable integration, predictive analytics, and personalized recommendations.
