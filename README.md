# Spin the Wheel 🎡 - Customizable Wheel View for Android

Spin the Wheel is a highly customizable and interactive wheel view designed for Android
applications. It allows developers to seamlessly integrate a spinning wheel feature into their apps,
perfect for creating engaging experiences like games, raffles, decision-making tools, and more.

## 🌟 Key Features

| **Key Features**         |                                                                             |
|--------------------------|-----------------------------------------------------------------------------|
| **Customizable Design**  | Easily adjust colors, segments, text, and more to fit your app's theme.     |
| **Smooth Animations**    | Provides a realistic spinning effect with customizable duration and easing. |
| **Flexible Integration** | Simple setup with minimal configuration to get started quickly.             |
| **Callback Support**     | Listen for spin results and perform actions based on user interactions.     |
| **Multiple Modes**       | Support for both normal spinning and visual slice selection modes.          |

Whether you're building a game of chance, a prize wheel, or a fun decision-making app, Spin the
Wheel brings creativity and engagement to your Android projects.

## Table of Contents

1. [Introduction](#spin-the-wheel----customizable-wheel-view-for-android)
2. [Key Features](#-key-features)
3. [Example](#-example)
4. [Installation](#-installation)
    - [Gradle Kotlin DSL](#gradle-kotlin-dsl)
    - [Gradle](#gradle)
    - [Maven](#maven)
5. [Usage](#-usage)
    - [Add Wheel View to Layout](#1-add-wheel-view-to-layout)
    - [Configure in Code](#2-configure-in-code)
    - [Create Wheel Item](#3-create-wheel-item)
    - [Wheel Modes](#4-wheel-modes)
6. [Structure and Properties](#-structure-and-properties)
    - [Main Components](#main-components)
    - [Wheel Item Properties](#wheel-item-properties)
    - [Container Attributes](#container-attributes)
    - [Main Methods](#main-methods)
7. [Support and Contributions](#-support-and-contributions)
8. [License](#-license)

## 📱 Example

| <img src="https://github.com/user-attachments/assets/f35e1e7b-3c53-4a48-8eaf-3eaba3c2bdf7" alt="" width="300" height="300" /> | <img src="https://github.com/user-attachments/assets/22a04eed-7319-4cae-99de-6ba7008a16c9" alt="" width="300" height="300" /> |
|-------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/user-attachments/assets/fb65920e-ad6f-4b12-85dc-9b467e53340d" alt="" width="300" height="300" /> | <img src="https://github.com/user-attachments/assets/cbf1810f-9f78-4ba1-b054-8933ee7acf92" alt="" width="300" height="300" /> |

## 📦 Installation

[![](https://jitpack.io/v/VuNgN/SpinTheWheel.svg)](https://jitpack.io/#VuNgN/SpinTheWheel)

### Gradle Kotlin DSL

1. Add Jitpack to your project-level `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}
```

2. Add the library dependency to your module-level `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.VuNgN:SpinTheWheel:latest-version")
}
```

### Gradle

1. Add Jitpack to your project-level `settings.gradle`:

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the library dependency to your module-level `build.gradle`:

```groovy
dependencies {
    implementation 'com.github.VuNgN:SpinTheWheel:latest-version'
}
```

### Maven

1. Add Jitpack to your `pom.xml`:

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

2. Add the library dependency:

```xml

<dependency>
    <groupId>com.github.VuNgN</groupId>
    <artifactId>SpinTheWheel</artifactId>
    <version>latest-version</version>
</dependency>
```

## 🚀 Usage

### 1. Add Wheel View to Layout

```xml

<com.vungn.luckywheel.LuckyWheel android:id="@+id/lwv" android:layout_width="400dp"
    android:layout_height="400dp" android:layout_gravity="center" LuckyWheel:arrow_height="100dp"
    LuckyWheel:arrow_image="@drawable/ig_arrow_1" LuckyWheel:arrow_width="100dp"
    LuckyWheel:background_color="@android:color/darker_gray" LuckyWheel:border_color="#33FFFFFF"
    LuckyWheel:border_src="@drawable/ig_edge_1" LuckyWheel:border_width="30dp"
    LuckyWheel:font_family="@font/roboto_bold" LuckyWheel:shadow_src="@drawable/ig_shadow_1"
    LuckyWheel:text_padding="10dp" />
```

### 2. Configure in Code

```kotlin
val lw = binding.lwv
val wheelItems: List<WheelItem> = emptyList()

// Add items to wheel
lw.addWheelItems(wheelItems)

// Listen for finish event
lw.setLuckyWheelReachTheTarget { item: WheelItem ->
    // Handle when target item is reached
}

// Listen for target change events
lw.setOnTargetChanged { item: WheelItem ->
    // Handle when wheel passes through a slice
}

// Listen for interaction events
lw.setListener(object : WheelListener {
    override fun onSlideTheWheel() {
        // Handle when user slides the wheel
    }

    override fun onTouchTheSpin() {
        // Handle when user touches the spin button
    }
})

// Get random target
val randomNum = WheelUtils.getRandomIndex(wheelItems)

// Start spinning the wheel
lw.rotateWheelTo(randomNum)
```

### 3. Create Wheel Item

```kotlin
val item = WheelItem(
    id = 0,
    text = "Item 1",
    textColor = Color.WHITE,
    backgroundColor = Color.RED,
    probability = 1
)
```

### 4. Wheel Modes

The wheel supports two different modes that can be set using `setWheelMode()`:

```kotlin
// Set wheel mode
lw.setWheelMode(WheelMode.NORMAL) // or WheelMode.VISUAL
```

#### NORMAL Mode

- Default mode for standard wheel spinning functionality
- Allows wheel rotation and spin animations
- Supports all standard wheel interactions
- Use this mode for traditional wheel spinning applications

#### VISUAL Mode

- Disables wheel spinning functionality
- Enables direct slice selection through touch
- Allows users to interact with individual slices
- Perfect for selection-based interfaces or preview modes
- Use this mode when you want users to manually select slices

To handle slice selection in VISUAL mode, implement the slice click listener:

```kotlin
lw.setSliceClick { item: WheelItem ->
    // Handle slice selection
    // This will only work in VISUAL mode
}
```

## 📊 Structure and Properties

<img src="https://github.com/user-attachments/assets/0fa46a70-2940-474b-96bc-109c6b390e2b" alt="" width="600" height="300" /> 

### Main Components

1. Border (Edge)
2. Arrow
3. Shadow
4. Slice
5. Text

### Wheel Item Properties

| Property          | Type     | Description                                                                                      |
|-------------------|----------|--------------------------------------------------------------------------------------------------|
| `id`              | `Int`    | Unique identifier for the slice                                                                  |
| `text`            | `String` | Text to display on the slice                                                                     |
| `textColor`       | `Int`    | Color of the text                                                                                |
| `backgroundColor` | `Int`    | Background color of the slice                                                                    |
| `probability`     | `Int`    | Relative weight of the slice in random selection. Higher values increase the chance of selection |

### Container Attributes

| Component        | Attribute              | Related Method              | Default Value                                                                                                               |
|------------------|------------------------|-----------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| Edge Resource    | `app:border_src`       | `setWheelBorder()`          | `Color.TRANSPARENT`                                                                                                         |
| Edge Width       | `app:border_width`     | `setWheelBorderWidth()`     | `None`                                                                                                                      |
| Background Color | `app:background_color` | `setWheelBackgroundColor()` | `Color.TRANSPARENT`                                                                                                         |
| Shadow           | `app:shadow_src`       | `setWheelShadow()`          | `None`                                                                                                                      |
| Arrow Image      | `app:arrow_image`      | `setArrowImage()`           | <img src="https://github.com/user-attachments/assets/dad494a5-25eb-4272-89e1-9582427d688e" alt="" width="40" height="50" /> |
| Arrow Width      | `app:arrow_width`      | `setArrowWidth()`           | `50dp`                                                                                                                      |
| Arrow Height     | `app:arrow_height`     | `setArrowHeight()`          | `50dp`                                                                                                                      |
| Text Padding     | `app:text_padding`     | `setTextPadding()`          | `0`                                                                                                                         |
| Text Size        | `app:text_size`        | `setTextSize()`             | `15sp`                                                                                                                      |
| Font Family      | `app:font_family`      | `setFontFamily()`           | `None`                                                                                                                      |

### Main Methods

| Method                          | Parameters                    | Description                        |
|---------------------------------|-------------------------------|------------------------------------|
| `addWheelItems()`               | `List<WheelItem>`             | Add items to the wheel             |
| `setLuckyWheelReachTheTarget()` | `OnLuckyWheelReachTheTarget`  | Listen for completion event        |
| `setOnTargetChanged()`          | `OnTargetChanged`             | Listen for slice transition events |
| `setSliceClick()`               | `OnSliceClick`                | Listen for slice selection events  |
| `setWheelMode()`                | `WheelMode`                   | Set wheel mode (NORMAL or VISUAL)  |
| `setListener()`                 | `WheelListener`               | Listen for interaction events      |
| `rotateWheelTo()`               | `Int`                         | Start spinning to target position  |
| `resetWheel()`                  | `None`                        | Reset wheel to initial state       |
| `setSliceRepeat()`              | `Int`                         | Set number of slice repetitions    |
| `setSpinTime()`                 | `Int` or `SpinTime`           | Set rotation duration              |
| `setWheelEdgeColor()`           | `Int`                         | Set edge color                     |
| `setWheelEdgeWidth()`           | `Int`                         | Set edge width                     |
| `setWheelBackgroundColor()`     | `Int`                         | Set background color               |
| `setWheelShadow()`              | `Int` or `Bitmap`             | Set shadow image                   |
| `setArrowImage()`               | `Int`, `Bitmap` or `Drawable` | Set arrow image                    |
| `setArrowSize()`                | `(Int, Int)`                  | Set arrow dimensions               |
| `setTextPadding()`              | `Int`                         | Set text padding                   |
| `setTextSize()`                 | `Float`                       | Set text size                      |
| `setFontFamily()`               | `Int`                         | Set font family                    |

## 🤝 Support and Contributions

Support the project by:

- ⭐ Star this repository
- 👥 Follow me for updates on new products
- 🐛 Report bugs
- 💡 Suggest new features
- 🔧 Contribute code

## 📄 License

```text
Copyright 2024 VuNgN (Nguyễn Ngọc Vũ)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
