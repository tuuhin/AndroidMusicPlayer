# 🎵 MusicPlayer

## 📔 Description

The Music Player as the app name states is an Android application that allows the user to play music
with a media style notification to control media actions like rewind,forward repeat.These project
was intended to learn about `content resolvers` and `media3`.

## 👉 Facts

The app seems well enough, it can fulfill the basic requirements of an music app

- Can read the music (audio) files, list out the audio files to the user that is currently.
- Can play the current selected music with a proper media notification.

But there are 😔 `some flaws` that should be mentioned its not properly handled as per the UX point of
view especially with the activity intent, as a simple app its working fine but when compare to other
apps it has a kind a normal UX.

## ✔️ Features

- **Reading Audio Files:** Using content resolvers we can read any type of files, its like a huge
  database you need to give a particular query to get out the results,here out query was get me all
  audio files

- **Media 3 Implementation:** Used media 3 to play those audio files,and being the first audio/music
  player related project it was very exiting.Failed in some aspects but the results were better than
  expected

- **Background player:** As our typical music players, implemented the music mini player or the
  media notification with player specific controls

## 🖼️ ScreensShots

These are some of the screen shots of the app.

<div align="center">
  <img width="24%" src="screenshots\screenshot_1.png" />
  <img width="24%" src="screenshots\screenshot_2.png" />
  <img width="24%" src="screenshots\screenshot_3.png" />
</div>

## 🔚 Conclusion

Its really hard to implement audio player especially with the media notification, thanks to
this [issue](https://github.com/androidx/media/issues/216) this helped me quite a lot in properly
implementing the player notification and media3 most of the things a still `unstable` but the
concept of controller player and sessions are seems good, when stable maybe bit easier to implement
the same app, as per the app its quite good enough.
