# chittr-microblog #

A mobile app interface to interact with the Chittr microblog API <br/><br/>
[![JavaScript Style Guide](https://img.shields.io/badge/code_style-standard-brightgreen.svg)](https://standardjs.com)


## Installation ##

Install React Native and Android Studio following the instructions at:

https://reactnative.dev/docs/getting-started.html

and:

https://developer.android.com/studio/install

After installling Android Studio follow these instructions:

https://developer.android.com/studio/run/emulator

to set up an emulator of your choice.

Clone this repository and navigate into the Chittr folder:

`cd ./Chittr`

Install dependencies with:

`npm install`

In Android Studio select open an existing project and select the android folder
inside the Chittr folder and allow the project time to build.

## Usage ##

Add a google API key to line 24 in the ./Chittr/src/components/chits.js file.
This API key requires geocoding enabled.

Open ./Chittr/index.js and change the URL on line 22 to point to wherever you
have the Chittr API running.

In the ./Chittr folder run the project on the Android Studio emulator with the
following command:

`npx react-native run-android`

## Contribution ##

This is an educational project and not looking for contributions at this time.

Feedback is welcome. 