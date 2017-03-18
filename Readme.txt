The Tourist guide application was is developed using Java and Restful APis

Necessary external jar json-simple-1.1.1.jar in the folder named lib file(in project folder name TouristGuide) and countries.txt(in project folder name TouristGuide) have been attached with the submission and please make sure you have UTF-8 encoding set for this to project(for translation feature as explained below)

There are two methods to run the application by using touristGuide.jar and by Eclipse see the following steps for the same

1.To run the application through touristGuide.jar which is runnable executable of the application go to command prompt and go to the directory where touristGuide.jar is stored and write this command   java -Dfile.encoding=UTF-8 -jar touristGuide.jar   (since the application requires utf -8 encoding for translation) after this go to step 4
2.To run the application using Eclipse first import the whole project named TouristGuide into the eclipse Go to File ---> Import--->  General ---> Existing java Project and go the folder where entire project package is stored  and select that(to set the utf -8 encoding go to eclipse --> File--> Properties  --> and set encoding to UTF-8 from the dropdown) in case the imported project throws errors due to external jar files put them into build path from the folder named lib(in project folder name TouristGuide)
3.To run the application  trough eclipse run the TouristGuide.java and please make sure the file named countries.txt(in project package name TouristGuide) which is attached with this (submission) is present in the same folder as in source files
4.The application developed in Java Swing will pop up
5.First step towards using the application is to select the Source Country and Destination Country from the dropdown menu
6.After selecting the countries click on “Get Details” Button which will populate the one or many currencies and one or many languages used in both the countries.
7.The list for currencies  is dynamically populated in the drop down for both the countries and the language used in destination country will also be populated in the dropdown for selecting language(in case there are multiple languages)
8.Select the currencies for both the destination and source country. To get  details about the current exchange rate between two counties click on “Get Exchange Rate” button which will show the value just below it
9.To translate the Text written in English to the native language of the destination country, first write some valid text in English in the text area  and select language from the dropdown and click on “Translate” button, the translated text will appear in the text area present next to it
10.Make sure you have UTF-8 encoding set for this to work(as explained above)
11.Enter the name of the location of the destination country you are visiting in the textbox present and select the type of places you are looking for, around that location e.g. Food, hospital, banks etc. and hit on Search Place Button
12.Based on the selection type, a list consisting of the name and a Google maps URL for those types of places will be populated in the big Text area 

