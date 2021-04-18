# test accelerator
This project became commercially viable test accelerator framework reviewed and accepted for use.

The code base here is the pre-acceptance code base, no commercially developed enhancements are embedded

## Motivaton
It was not making sense to replicate a significant code base on every commercial engagement.

## Configuration
The project consisted of the core libraries needed to be reused on many clients using:
* testNg
* Cucumber
* Selenium
* Rest and SOAP api
* File manipulation (find file, wait for file to be consumed, scan file for value, update file)
* testng and cucumber hooks

## What I had to Learn to create this
To create this project I had to learn java:
* Annotations
* Reflection
* TestNG runner capabilities

The code is capable of running local and remote web drivers, mobile devices/simulators and connecting with BrowserStack, SauceLabs and related services.
