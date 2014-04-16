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

