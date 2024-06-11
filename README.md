### Price Ninja is a Kotlin Multiplatform project targeting Android, iOS and Server.
#### This is a course project for the Ukrainian Academy of Printing

<div align="center">
    <img src="https://raw.githubusercontent.com/Buffersolve/PriceNinja/develop/composeApp/src/androidMain/res/mipmap-xxxhdpi/ic_launcher_round.webp" alt="Logo 1" style="margin-right: 20px;">
    <img src="https://scontent.flwo3-1.fna.fbcdn.net/v/t39.30808-6/295348055_469187578545303_2621973207460937108_n.png?_nc_cat=107&ccb=1-7&_nc_sid=5f2048&_nc_ohc=0wipK874cAAQ7kNvgGcpF1M&_nc_ht=scontent.flwo3-1.fna&oh=00_AYAY9CTGwt2niX3_SMPVSKSX46Z-2rQhQcwf29YgV8xGDQ&oe=666E370A" alt="Logo 2" style="height: 200px;">
</div>


#### Preview

<img width="300" src="" />


#### Project structure

```
* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* `/server` is for the Ktor server application.

* `/shared` is for the code that will be shared between all targets in the project.
  The most important subfolder is `commonMain`. If preferred, you can add code to the platform-specific folders here too.
```

  ## License
```
MIT License

Copyright (c) 2024 Buffersolve

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```