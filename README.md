# SpaceFlightApp
A simple mobile app that consumes the LL2 (Launch Library 2) APIs. The app shows upcoming rocket launches , previous rocket launches , agency record and rocket record.
The Api utilised in this application is the developer version and is not throttled meaning a single device can make multiple api requests. However the data coming fom the developer
version is stale and not real-time.
For the best version of the api please go to ll.thespacedevs.com , support them on Patreon to get an api key to access their real-time and better api.

This application uses the following features:
1. Material Design
2. Jetpack library which includes:
- ROOM database
- Hilt for dependency injetion
- Paging3 library
- Navigation component
3. MVVM architecture
4. ViewPager3
5. Retrofit library
6. Glide 

The app consists of one Activity and six fragments:
1. Main activity holds the navigation container for all the fragments
2. Home fragments contains the tablayout linked  with a viewpager to show and navigate between the upcoming launches fragment
   and previous launches fragment.
3. Upcoming launches fragment contains a list of all the upcoming rocket launches accross the globe
4. Previous launches fragment contains a list of all previous launches/missions
5. Launch details fragment contains details of the selected launch either previous or upcoming including agency details and mission status and details.
6. Rocket details fragment contains the details of the rocket being flown on that mission.

If you like the project please give it a star, clone and customise it to your wish ,  also follow me on Twitter for any questions: @JamesTravor



