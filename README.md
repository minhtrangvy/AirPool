AirPool
===============

ABOUT:

AirPool is an Android application that allows college students to coordinate
rides to and from airports in the Los Angeles area.

DEPENDENCIES:

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

TESTING:

All tests are within the app/src/androidTest folder. Our test suite uses both JUnit tests and
Robotium tests. All Robotium tests are labelled with "Robotium" in the test class name.