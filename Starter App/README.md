This is the readme file for the starter app. 
We utilized the sample starter app for our front end so our folder format will follow like the sample.

Within the project sample folder will be 3 other folders:

1. the .idea folder
2. the app folder
3. the gradle/wrapper folder.

The main relevant code will be in the folder of the path android-project-sample-f17/app/src/main/java/com/tiberiuvilcu/chatter/

The Chatt.java file is just the definition of the Chatt object and its constructor which just stores username, message and timestamp.

The ChattAdapter.java file gets the view of the array of saved messages.

The PostActivity.java fi;e mainly makes the post request to the backend server which ends up creating the message in the application.

The TimelineActivity.java file mainly handles the get request which handles the refreshing of the message board and just getting the message board itself.



To Build and run the code:

1. Make sure to have android studio and have virtualization along with a virtual device downloaded and enable.
2. Then pull the files from github.
3. Once you have the files, build the project on your android studio, some gradle and/or configuration updates may occur.
4. Once all the updates have occurred and the syncing is done, you should be able run the app in debug mode and the application
should open up in the virtual phone.
