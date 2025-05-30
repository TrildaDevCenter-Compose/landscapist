/*
 * Designed and developed by 2020-2023 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.github.skydoves.landscapist.Configuration

plugins {
  id("landscapist.library.compose")
  id("landscapist.spotless")
}

apply(from = "${rootDir}/scripts/publish-module.gradle.kts")

mavenPublishing {
  val artifactId = "landscapist-transformation"
  coordinates(
    Configuration.artifactGroup,
    artifactId,
    rootProject.extra.get("libVersion").toString()
  )

  pom {
    name.set(artifactId)
  }
}

android {
  namespace = "com.skydoves.landscapist.transformation"
  compileSdk = Configuration.compileSdk

  defaultConfig {
    minSdk = Configuration.minSdk
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    externalNativeBuild {
      cmake {
        cppFlags += "-std=c++17"
        arguments += listOf("-DANDROID_SUPPORT_FLEXIBLE_PAGE_SIZES=ON")
      }
    }
    consumerProguardFiles("consumer-rules.pro")
  }
  externalNativeBuild {
    cmake {
      path = file("src/main/cpp/CMakeLists.txt")
    }
  }
}

baselineProfile {
  baselineProfileOutputDir = "."
  filter {
    include("com.skydoves.landscapist.transformation.**")
  }
}

dependencies {
  api(project(":landscapist"))

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.androidx.compose.foundation)

  androidTestImplementation(libs.androidx.test.rules)
  androidTestImplementation(libs.androidx.test.runner)
  androidTestImplementation(libs.androidx.test.junit)
  androidTestImplementation(libs.androidx.compose.ui)
  androidTestImplementation(libs.androidx.compose.ui.test)
}