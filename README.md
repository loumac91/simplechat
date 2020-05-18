# CM50275 Simple Chatting System

Everything in this submission was developed in Visual Studio Code

The following steps show how I tested the application

## How to compile
javac *.java

## How to run
java ChatServer - must be run first
java ChatClient
java ChatBot

## Provide command line arguments

### Change server port
java ChatServer -csp 14005 

### Change host client tries to connect to
java ChatClient -cca 192.168.10.250

### Change port client tries to connect to
java ChatClient -ccp 14005

### Change both port and host client tries to connect to
java ChatClient -cca 192.168.10.250 -ccp 14005

Note that if these parameters cannot be found or parsed to valid values, then fallback default options will be used

## Disconnecting Client
Just used ctrl + c in terminal to terminate client - server should handle this cleanly

## Shutting down server
Type "EXIT" into server terminal

## Interacting with ChatBot
ChatBot will read all incoming messages from the server and detect phrases such as
what time is it
what day is it
and will respond with the time
If a user private messages ChatBot - ChatBot will respond privately as well

## Private messaging
@{username} message
e.g. @joe hey joe