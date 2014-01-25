Rails Time - Train Simulator
=======================

Rails Time is a Java based application that uses Spring 4. 

What it does is simply crawl rails timetables using Mechanize, store the data to a Mongo Database and then exposes that with some Location simulation via RESTful interface:

[/service/train] - Return all Trains
[/service/train/{trainId}] - Return just the train for a given Id
[/service/train/type] - Return all Trains for a given Type
[/service/train/type/{type}] - Return all Trains grouped by Type
[/service/train/origin] - Return all Trains grouped by Origin
[/service/train/origin/{origin}] - Return all Trains for a given Origin


![alt tag](https://dl.dropboxusercontent.com/spa/y32fwqxnzbyqfvp/qpdmcu51.png)
