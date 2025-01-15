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

| <img src="https://github.com/user-attachments/assets/f35e1e7b-3c53-4a48-8eaf-3eaba3c2bdf7" alt="" width="300" height="300" /> | <img src="https://github.com/user-attachments/assets/22a04eed-7319-4cae-99de-6ba7008a16c9" alt="" width="300" height="300" /> |
| ------------- | ------------- |
| <img src="https://github.com/user-attachments/assets/fb65920e-ad6f-4b12-85dc-9b467e53340d" alt="" width="300" height="300" /> | <img src="https://github.com/user-attachments/assets/cbf1810f-9f78-4ba1-b054-8933ee7acf92" alt="" width="300" height="300" /> |

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
android:background="@drawable/ig_edge_1"
LuckyWheel:arrow_image="@drawable/ig_arrow_1"
LuckyWheel:shadow_src="@drawable/ig_shadow_1"
LuckyWheel:border_color="#33FFFFFF"
LuckyWheel:border_width="30dp"
LuckyWheel:font_family="@font/roboto_bold"
LuckyWheel:text_padding="10dp" />
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
<img src="https://github.com/user-attachments/assets/0fa46a70-2940-474b-96bc-109c6b390e2b" alt="" width="600" height="300" /> 

1. Border
2. Arrow
3. Shadow
4. Slice
5. Text

#### Container attributes
| Element     | Attribute     | Related method(s) | Default value |
| ------------- | ------------- | ------------- | -------------- |
| Border color | `LuckyWheel:border_color` | `setWheelBorder()` | `Color.TRANSPARENT` |
| Border width | `LuckyWheel:border_width` | `setWheelPadding()` | `None` |
| Shadow | `LuckyWheel:shadow_src` | `setWheelShadow()` | `None` |
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
