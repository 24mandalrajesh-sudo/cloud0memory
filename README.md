# Cloud Memory — Android starter

Dark-themed Android starter project for a cloud-storage dashboard.

## Current status
This is a UI/source-code starter, **not a finished cloud-connected app and not a compiled APK**.
- Dark dashboard UI
- Local folder access request via Android's Storage Access Framework
- Google Drive and TeraBox setup placeholders
- No cloud credentials are collected or stored

## Cloud integrations needed
### Google Drive
Set up a Google Cloud project, enable the Google Drive API, configure OAuth consent and an Android OAuth client, then implement the supported sign-in and Drive API flow. Do not embed client secrets in the APK.

### TeraBox
Implement only through an official supported API or authorized integration. Do not scrape private endpoints or ask users to type their account password into this app.

## Build
Open this folder in Android Studio with JDK 17 and Android SDK Platform 35, allow Gradle sync, then choose **Build > Build Bundle(s) / APK(s) > Build APK(s)**. The output APK is typically under `app/build/outputs/apk/debug/`.

You can also use an Android-compatible cloud build service, but review its privacy and project-upload policies first.

## Important limitation
Cloud storage cannot simply be merged into physical Android internal memory. This app can grow into a unified file manager, with provider-backed file access where each service and Android version permit it.
