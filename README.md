# Cab Allocation System

## Introduction
The Cab Allocation System is a comprehensive solution that seamlessly connects users with available drivers, employing a combination of efficient driver categorization, geolocation services, and secure user authentication. This system enhances the overall user experience by ensuring not only optimal cab allocation but also prioritizing user security through email and password authentication.

## DB Modals
<img src="https://github.com/AnshTandon0/smartcaballocation/assets/75798189/0d5ac882-4882-4418-a18f-83e64e8beb8d"  width="1200" height="500">

## App Logo
<img src="https://github.com/AnshTandon0/smartcaballocation/assets/75798189/510b706a-13d9-4bf6-a858-a48eddd13f9a"  width="300" height="300">

## Application Link
```
https://drive.google.com/file/d/15bKqkTXRF6FX6v4skVbeN8yZmN07DMK9/view?usp=drive_link
```

## Features of the App

### User Authentication

User authentication is implemented through a secure email and password system.
Users are required to authenticate themselves, maintaining the security of the system and ensuring that only authorized individuals can access and use the service.

### User Interface

A user-friendly interface provides users with real-time information on available and free cabs.
Users can also securely log in, view available cabs , and book them through the authenticated interface.

### Cab Data Storage

Drivers are categorized and stored based on their current location pincode, facilitating the quick identification of the nearest available driver for a given ride request.

### Allocation Algorithm

Upon receiving a ride request, the system utilizes an advanced allocation algorithm that considers real-time driver locations of drivers at same pincode , calculating the distance to determine the most suitable driver.
The nearest available driver is automatically recommended to the user , optimizing efficiency.


### Geolocation API Integration

The system integrates a geolocation API to convert latitude and longitude coordinates into human-readable textual addresses.
This feature enhances user understanding of the location of available cabs and their own pickup point.

## Working Demo
```
https://drive.google.com/file/d/18RTAwmqi5i2SoGDWEWMCsq5x2k34RGkZ/view?usp=drive_link
```

## Key Components of the Application

### Admin's Cab Allocation Optimization:

The Admin's Cab Allocation Optimization module is a crucial component within our system that empowers administrators with advanced tools to enhance the efficiency of cab allocation. Key features of this module include:

- **Intelligent Dashboard** : Administrators are provided with an intuitive dashboard that offers real-time insights into the status and availability of cabs. 
- **Dynamic Allocation Algorithms** : Advanced algorithms enable the system to dynamically allocate cabs. This ensures optimal utilization of resources and minimizes response times.

#### Working:
- Cab Location are stored according to postcode ( horizontal partitioning of data ) , so according to the current user postcode , all the cabs currently at that postcode are shown .
- The distance for all the cabs is calculated using haversine formula , and the cab at least distance is recommended . 


### Employee's Cab Search Optimizaztion
The Employee's Cab Search Optimization feature is designed to streamline the process of finding and booking cabs for users. This user-centric module aims to provide a seamless and efficient experience for employees seeking transportation services. Key elements of this module include:

- **Intuitive User Interface** : The user interface is designed to be user-friendly, allowing employees to easily input their ride preferences, view available cabs, and track their allocated cab in real-time.

- **Instant Ride Confirmation** : The module provides employees with real-time information on available cabs. Once a suitable cab is identified, the booking process is streamlined to provide instant ride confirmation, minimizing wait times.


#### Working
- As Cabs are stored accoding to the postcode , so this actually enhances the search , saving a lot of time , as only the specific collection need to be read .
- The Users latitude and longitude is reverse geocoded to get postcode which is actually used to fetch cabs .


### Realtime Location Data Integration

Real-time Location Data Integration is a critical aspect of our cab allocation system, enabling accurate and up-to-date information on the location of both drivers and users. Key features of this integration include:

- **Geolocation API Integration** : The system seamlessly integrates with a geolocation API to convert latitude and longitude coordinates into easily understandable textual addresses. 

- **Live Tracking** : Real-time location tracking ensures that administrators, drivers, and users have access to live updates on the current location of cabs. This feature is crucial for optimizing cab allocation, managing ride progress, and improving overall system efficiency.

#### Working

- The Real-time Location Data Integration relies on Reverse GeoCoding APIs to seamlessly convert latitude and longitude coordinates into human-readable textual addresses.
- Live tracking ensures that administrators, drivers, and users have access to real-time updates on the current location of cabs, facilitating efficient management of the transportation network.

### TechStack and Libraries Used :

* Android Studio (Kotlin + XML)
* Retrofit
* MVVM
* Hilt
* Dagger
* Coroutines
* Reverse Geocoding API
* Firebase Auth
* FireStore

## Conclusion

The Cab Allocation System with User Authentication offers a robust solution for connecting users with the nearest available cabs while prioritizing security through email and password authentication. This holistic approach ensures an efficient, transparent, and secure user experience, enhancing overall user satisfaction and operational effectiveness.
