# Spin the Wheel 🎡 - Customizable Wheel View for Android

Spin the Wheel is a highly customizable and interactive wheel view designed for Android applications. It allows developers to seamlessly integrate a spinning wheel feature into their apps, perfect for creating engaging experiences like games, raffles, decision-making tools, and more.

| **Key Features** |       |
| ------------- | ------------- |
| **Customizable Design** | Easily adjust colors, segments, text, and more to fit your app's theme. |
| **Smooth Animations** | Provides a realistic spinning effect with customizable duration and easing. |
| **Flexible Integration** | Simple setup with minimal configuration to get started quickly. |
| **Callback Support** | Listen for spin results and perform actions based on user interactions. |

Whether you're building a game of chance, a prize wheel, or a fun decision-making app, Spin the Wheel brings creativity and engagement to your Android projects.

## Table of Contents

1. [Introduction](#spin-the-wheel--customizable-wheel-view-for-android)
2. [Key Features](#key-features)
3. [Example](#example)
4. [Installation](#installation)
   - [Gradle Kotlin DSL](#gradle-kotlin-dsl)
   - [Gradle](#gradle)
   - [Maven](#maven)
5. [Usage](#usage)
   - [XML Layout](#in-the-layout)
   - [Code Integration](#in-code)
   - [Anatomy and Key Properties](#anatomy-and-key-properties)
6. [Support and Contributions](#support-and-contributions)
7. [License](#license)

## Example
| <img src="https://github.com/user-attachments/assets/24ff951f-e324-499f-9364-96abe5a3bee7" alt="" width="200" height="440" /> | <img src="https://github.com/user-attachments/assets/fac96e86-0a94-4acf-9e42-986d8c71ae9a" alt="" width="200" height="440" /> | <img src="https://github.com/user-attachments/assets/f50cd4b3-ff3e-4b13-b4d6-0e9c54b47290" alt="" width="200" height="440" /> |
| ------------- | ------------- | ------------ |

## Installation

[![](https://jitpack.io/v/VuNgN/SpinTheWheel.svg)](https://jitpack.io/#VuNgN/SpinTheWheel)

### Gradle Kotlin DSL

1. Add Jitpack to your project-level `settings.gradle.kts`:
    ```kotlin
   dependencyResolutionManagement {
       repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
       repositories { 
            ...
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

## Usage

In the layout:

```xml
<com.vungn.luckywheel.LuckyWheel
    android:id="@+id/lwv"
    android:layout_width="400dp"
    android:layout_height="400dp"
    android:layout_gravity="center"
    LuckyWheel:background_color="@color/colorPrimary"
    LuckyWheel:font_family="@font/roboto_bold"
    LuckyWheel:text_color="#FFFFFF"
    LuckyWheel:text_padding="10dp"
    LuckyWheel:text_size="20sp" />
```

In code:

```kotlin
val lw = binding.lwv;
val wheelItems: List<WheelItem> = emptyList()

// add items to the wheel view
lw.addWheelItems(wheelItems)

// listen for the finish event
lw.setLuckyWheelReachTheTarget { item: WheelItem ->
  // TODO: when the target item is reached
}

// listen for action events
lw.setListener(object : WheelListener {
  override fun onSlideTheWheel() {
    // TODO: when the wheel is swiped 
  }

  override fun onTouchTheSpin() {
    // TODO: when pressing the wheel arrow
  }
})

// get random target
val randomNum = WheelUtils.getRandomIndex(wheelItems)

// start spinning the wheel
lw.rotateWheelTo(randomNum)
```

**Note** I recommend using the `WheelUtils.getRandomIndex(wheelItems)` method to get random number for the target. If not, you need to calculate the total number of slices including the total probability of each slice. To get the total probability, use the `WheelUtils.calculateTotalProbability(wheelItems)` method.

### Anatomy and Key properties
<img src="https://github.com/user-attachments/assets/79d6cad2-ec37-4cd2-8538-ba8e8b1b029d" alt="" width="600" height="300" /> 

1. Arrow
2. Slice
3. Text
4. Background

#### Container attributes
| Element     | Attribute     | Related method(s) | Default value |
| ------------- | ------------- | ------------- | -------------- |
| Background color | `LuckyWheel:background_color` | `setWheelBackgroundColor()` | `None` |
| Arrow image | `LuckyWheel:arrow_image` | `setArrowImage()` | <img src="https://github.com/user-attachments/assets/dad494a5-25eb-4272-89e1-9582427d688e" alt="" width="40" height="50" />  |
| Padding between text and edge | `LuckyWheel:text_padding` | `setTextPadding()` | `0` |
| Text size | `LuckyWheel:text_size` | `setTextSize()` | `15sp`|
| Text color | `LuckyWheel:text_color` | `setTextColor()` | `Color.WHITE` |
| Font family | `LuckyWheel:font_family` | `setFontFamily()` | `None` |

## Support and Contributions
Support it by joining [stargazers](https://github.com/VuNgN/EasyAdMob/stargazers) for this repository. ⭐</br>
And [follow](https://github.com/VuNgN) me for my next creations! 🤩

## License
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
