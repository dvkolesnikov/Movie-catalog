# Movie catalog app

The app allows the user to browse list of trending movies and get more details about them.

## Project architecture

The project is following the Clean architecture principles. There are 3 packages that refers to
architecture layers:

- data
- domain
- presentation

## Project technologies

##### Core:

- Kotlin
- Kotlin Coroutines
- Flow
- Koin

##### Data:

- OkHttp3
- Retrofit
- Moshi

##### UI and navigation:

- MVVM
- Jetpack Compose
- Compose navigation
- Coil

## Project build

`gradle clean build`

## Flavors

There are 2 flavors defined in gradle:

- dev
- prod

Even though they are actually the same for now they can be used to organise work in more safe and
effective way if there are more than one backend instance.