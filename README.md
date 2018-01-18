CONTAINS SERVERAL SAMPLE PROJECTS. NOT FOR PRODUCT USE
======================================================

jersey-docker-aws-poc
=====================
Sample project to dockerize very simple REST application based on jersey. 
Later use aws EC2 as a remote docker machine to run the docker image.


1. Created maven project from Jersey quick grizzly2 archetype

archetypeArtifactId=jersey-quickstart-grizzly2  
archetypeVersion=2.26

From <https://yurisubach.com/2016/07/14/jersey-dockerize/> 

In Main.java 
• updated the ipaddress to 0.0.0.0 so that process listens for all the ip addresses from container.
• Comment //  server.stop(); so that server will run till container runs

2. Create EC2 docker machine

docker-machine create --amazonec2-instance-type t2.micro  --driver amazonec2 --amazonec2-open-port 8000 --amazonec2-region ap-south-1 --amazonec2-access-key  AKIAJxxxxxxxxx  --amazonec2-secret-key Fafxxxxxxxxx aws-docker-api-sandbox
=> yellow highlighted port 8000 says, EC2 IAM to open up port for external access


3. Run the output of this command to make aws machine as active machine
C:\Users\naray\workspace\jersey-docker-aws-poc>docker-machine env aws-docker-api-sandbox


C:\Users\naray\workspace\jersey-docker-aws-poc>docker-machine ls
NAME                     ACTIVE   DRIVER      STATE     URL                       SWARM   DOCKER        ERRORS
aws-docker-api-sandbox   *        amazonec2   Running   tcp://35.154.62.xx:2376           v18.01.0-ce
=> above yellow highlighted IP address is where docker is running. So access any service in this ip address.


C:\Users\naray\workspace\jersey-docker-aws-poc>docker build -t jersey-docker-aws-poc .

docker run -d -p 8000:8080 --name webserver jersey-docker-aws-poc
CTRL+C to run in background

Docker logs webserver to view logs.


http://35.154.62.xx:8000/myapp/myresource








CODE PICKER
===========

Eclipse plugin to retrieve code snippets from within Eclipe Java editor.


Introduction
------------
 

Its better described as CopyPaste Programming. https://en.wikipedia.org/wiki/Copy_and_paste_programming

It is arguable whether a person is good/bad programmer if he copy pastes... It all depends on whether we understand what we reuse.

Anyways we do it and we will do it as a part of our work.

 

To aid us better on this side, I developed a plugin for eclipse. 

It fetches code snippets from few websites based on your search query with the comfort of being within eclipse.

This is just a beta version now, so all your comments are welcome. 

Currently it fetches data from 

 

http://snipt.net/

http://www.java-examples.com

http://stackoverflow.com/

 

If you people are already using some nice website to fetch sample code, do let me know I shall try to add it to the list.

 

This beta version supports only java snippets, for other technologies I will add it in future release.


Prerequisites
-------------
 

1.Eclipse 3.4 or more

2.JDK 1.5 or more

3.Internet connection => Proxy setting in Eclipse if required.

 

 

 

Installation
------------
 

Download the plugin from 
https://github.com/naraink/naraintools/releases/download/1.0.2/narainthots.tools.plugins.CodePickerPlugin_1.0.2.201403061447.jar 

Copy the plugin to Eclipse -> Plugins directory. Stop and Start eclipse once.

How to use it
-------------

1.Open in your java file like you do today.

2.Create a single line comment with “//” and then type in your search query, now use CTRL+SPACE to see the results.

3.You will get list of titles and as more information you will also be shown the actual snippet available.

4.Press enter to insert that piece of code.

