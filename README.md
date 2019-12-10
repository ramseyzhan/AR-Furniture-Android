AR Furniture 

===========================

|Author|Procastinators Now|
|---|---
|E-mail|best441team@umich.edu 
****
`Java` `Android` `Firebase`

* [ARFurnitureApp]
* [model_for_test]
****


## ARFurnitureApp

This is the place where we hold our demo AR Furniture App.

File Structure:

<pre>
|-- app
        |-- java
            |-- com.bongjlee.arfurnitureapp
                |-- ARViewPage.java
                |-- CompanyRegistrationForm.java
                |-- HomePage.java
                |-- MyArFragment.java
                |-- ProductPage.java
                |-- ProductSubmissionForm.java
                |-- UserAccountPage.java
                |-- UserEdit.java
                |-- UserLogin.java
                |-- UserLogout.java
                |-- render
                        |-- model3D
                                |-- controller
                                |-- entities
                                |-- model
                                |-- services
                                |-- util
                                |-- view
                        |-- util
        |-- res
            |-- layout
                |-- activity_ar_ui.xml
                |-- activity_comapny_registration_form.xml
                |-- activity_home_page.xml
                |-- activity_product.xml
                |-- activity_product_submission.xml
                |-- activity_user_account_page.xml
                |-- activity_user_login.xml
                |-- activity_user_sign_up.xml
                |-- toolbar.xml
|-- Gradle Scripts
        |-- build.gradle
        |-- settings.gradle
</pre>

Java files will handle all the behaviors of the app while interacting with Firebase and providing value
to the xml files.

Xml files display all the frontend layout and component of the application.

Gradle files handle the external library dependencies and project settings that the app utilize.

***Instructions:***

When you start the application (build and run the app on HomePage.java),
you would be on the home page and view the products as a guest.

By click the product card, you would be able to view the ProductPage.java and enter the AR mode
on the MyArFragment.java.

We have done the users sign-up and sign in system in the UserLogin.java and UserEdit.java.

After sign-in and register at CompanyRegistrationForm.java, you can upload the furniture details to our database.

## model_for_test

Here there are some sample models for you to choose.

