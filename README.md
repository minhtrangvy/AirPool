AirPool
===============

AirPool is an Android application that allows college students to coordinate
rides to and from airports in the Los Angeles area.

AirPool utilizes two main external APIs: Facebook API and Parse API.

The Facebook API is integrated with our code and is used in order to store unique users.
For an individual to use our app, they must log in with their Facebook account. Currently, there
is no other way for a user to create an account.

The Parse API is used to store user and group information. Each user needs to keep track of the
groups that they are a member of and the group must keep track of the ride information and users
who are members. The users also must be able to communicate with other group members, which they
can do via a group wall. Therefore, in our code we reference User Parse Objects, Group Parse
Objects, and WallPost Parse Objects. The three objects keep track of each other via many-to-many
Parse relations.

The structure of the directory is as follows: all main code is stored within the app/src/main
folder. From here, we have two folders: java and res. The java folder holds most of the back-end
code, while the res folder maintains the code for the user interface.

Within the java folder is a com.airpool folder. The com.airpool folder holds the java files that
relate to a specific activity, as well as a GlobalUser.java file and four folders. The four folders
are: Adapter, Fragment, Model, and View.

The java files that relate to a specific activity hold the code that relates to the activity's
xml file. All calls to Parse necessary are made within these files,rather than abstracted out.
This is because we did not have enough time to refactor the code. The GlobalUser.java file sets
the correct user and allows other activities to easily access the current user.

The Adapter folder holds three classes. These three classes hold code to allow the population
and display of the desired list of a specific group's members, the relevant groups given a
specific search result, and a specific user's groups.

The Fragment folder holds five classes. These classes are fragments that are called by the java
files that relate to a specific activity.

The Model folder holds six classes. Three of these classes store hard-coded information about
the airports, colleges, and transportation preferences that can be selected by users (Aiport,
College, and TransportationPreference). The other three classes subclass the User, Group, and
WallPost Parse Object methods.

The View folder holds five classes. Four of these classes relate to spinners, while the last class
is code for the splash screen.

Within the res folder are drawable folders, a layout folder, and values folder. The drawable
folders hold all the relevant icons and pictures that are used in the app. The layout folder
holds all the xml files for both the activities in the app and fragment views that are used
in the app. The values folder holds all the strings and styles used in the app.

All tests are within the app/src/androidTest folder. There is both JUnit tests and Robotium tests.
All Robotium tests are labelled as such.