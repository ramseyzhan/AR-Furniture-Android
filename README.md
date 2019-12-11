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
                |-- BuyingPage.java
                |-- CompanyRegistrationForm.java
                |-- EditPage.java
                |-- favorite_page.java
                |-- HomePage.java
                |-- MyArFragment.java
                |-- MyProductsPage.java
                |-- order_page.java
                |-- ProductPage.java
                |-- ProductSubmissionForm.java
                |-- See_review_page.java
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
                |-- activity_buying_page.xml
                |-- activity_comapny_registration_form.xml
                |-- activity_demo.xml
                |-- activity_demo_item.xml
                |-- activity_edit_product.xml
                |-- activity_favorite_page.xml
                |-- activity_home_page.xml
                |-- activity_my_products_page.xml
                |-- activity_product.xml
                |-- activity_product_submission.xml
                |-- activity_review_page.xml
                |-- activity_user_account_page.xml
                |-- activity_user_login.xml
                |-- activity_user_sign_up.xml
                |-- order_review_item.xml
                |-- product_item.xml
                |-- review_item.xml
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
Before running, make sure to have an android phone with debug mode enabled or have an emulator ready to go.

You should be able to pull from the repo and it should automatically have download the tools and assets required. Once there is a successful build, you can run it on an android phone or an emulator of your choice.

When you start the application (build and run the app on HomePage.java),
you would be on the home page and view the products as a guest.

By click the product card, you would be able to view the ProductPage.java and enter the AR mode
on the MyArFragment.java.

We have done the users sign-up and sign in system in the UserLogin.java and UserEdit.java.

After sign-in and register at CompanyRegistrationForm.java, you can upload the furniture details to our database.

## model_for_test

Here there are some sample models for you to choose.

