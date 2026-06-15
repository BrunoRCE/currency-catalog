# Currency Catalog 🌎

[![](https://jitpack.io/v/brunorce/currency-catalog.svg)](https://jitpack.io/#brunorce/currency-catalog)

[English](#english) | [Español](#español)

---

<a name="english"></a>
## English

A lightweight, platform-independent Kotlin/Java library providing a comprehensive catalog of global currencies, symbols, and metadata.

### ✨ Features

- 📦 **Comprehensive Catalog:** Access to a predefined list of common currencies.
- ⚙️ **System Detection:** Automatically identifies the local currency based on the system *Locale* and sets it as priority.
- 🚀 **Lightweight:** No heavy external dependencies.
- 🛠️ **Multiplatform (JVM):** Compatible with any Kotlin or Java project.

### 🚀 Installation

This library is available via [JitPack](https://jitpack.io/#brunorce/currency-catalog).

#### Gradle (Kotlin DSL)

1. Add the JitPack repository to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

2. Add the dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.brunorce:currency-catalog:v1.0.0")
}
```

### 🛠️ Usage

#### Get all currencies
`CurrencyProvider.getAll()` returns a list of currencies, placing the system's local currency in the first position (with ID 0).

```kotlin
import com.github.brunorce.currencycatalog.provider.CurrencyProvider

val currencies = CurrencyProvider.getAll()
currencies.forEach { currency ->
    println("${currency.code} - ${currency.name} (${currency.symbol})")
}
```

#### Find a currency by code
You can search for a specific currency using its ISO code (e.g., "USD", "EUR", "MXN").

```kotlin
val mxn = CurrencyProvider.findByCode("MXN")
if (mxn != null) {
    println("Found: ${mxn.name}")
}
```

#### 📄 Data Structure (Currency)
- `id`: Numeric identifier (0 for system currency).
- `code`: ISO currency code (e.g., "USD").
- `name`: Descriptive name.
- `symbol`: Currency symbol (e.g., "$").

---

<a name="español"></a>
## Español

Una librería ligera e independiente de la plataforma para Kotlin/Java que proporciona un catálogo completo de monedas globales, sus símbolos y metadatos.

### ✨ Características

- 📦 **Catálogo Completo:** Acceso a una lista predefinida de monedas comunes.
- ⚙️ **Detección del Sistema:** Identifica automáticamente la moneda local según el *Locale* del sistema y la posiciona como prioridad.
- 🚀 **Ligero:** Sin dependencias externas pesadas.
- 🛠️ **Multiplataforma (JVM):** Compatible con cualquier proyecto Kotlin o Java.

### 🚀 Instalación

Esta librería está disponible a través de [JitPack](https://jitpack.io/#brunorce/currency-catalog).

#### Gradle (Kotlin DSL)

1. Agrega el repositorio de JitPack a tu `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

2. Agrega la dependencia en tu `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.brunorce:currency-catalog:v1.0.0")
}
```

### 🛠️ Uso

#### Obtener todas las monedas
`CurrencyProvider.getAll()` devuelve una lista de monedas, colocando la moneda local del sistema en la primera posición (con ID 0).

```kotlin
import com.github.brunorce.currencycatalog.provider.CurrencyProvider

val currencies = CurrencyProvider.getAll()
currencies.forEach { currency ->
    println("${currency.code} - ${currency.name} (${currency.symbol})")
}
```

#### Buscar una moneda por código
Puedes buscar una moneda específica utilizando su código ISO (ej. "USD", "EUR", "MXN").

```kotlin
val mxn = CurrencyProvider.findByCode("MXN")
if (mxn != null) {
    println("Encontrada: ${mxn.name}")
}
```

#### 📄 Estructura de Datos (Currency)
- `id`: Identificador numérico (0 para la moneda del sistema).
- `code`: Código ISO de la moneda (ej. "USD").
- `name`: Nombre descriptivo.
- `symbol`: Símbolo de la moneda (ej. "$").

---
Developed by [brunorce](https://github.com/brunorce)
