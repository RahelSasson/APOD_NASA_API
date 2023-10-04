# Astronomy Photo Of The Day 

## Java project utilizing the NASAs API to display the Astronomy Photo of the Day, every day since 01/01/1996

The Hubble Space Telescope takes pictures non stop 24 hours a day, seven days a week. Hubble orbits at a speed of 17,000 miles per hour (27,000 kph) and completes one orbit approximately every 95 minutes. The entire observable universe is captured section by section. Every single day since the start of the year 1996, NASA scientists have chosen the best photo taken that day; named it and placed it in an archive with a short description. That archive is now available for use via the NASA APOD API. 

This application allows the user to choose any date  from 01/01/1996-current date and view the Astronomy Photo of the Day along with a short description of the image. 

![APOD1](https://github.com/RahelSasson/APOD_NASA_API/assets/96672758/ea7f3ad8-8d2a-4d8d-b376-165689126b22)

![APOD2](https://github.com/RahelSasson/APOD_NASA_API/assets/96672758/464878aa-3f12-462d-9a4a-589d9685ca75)


Website to generate API Key: [https://api.nasa.gov/]

HTTP Request: https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY


Query Parameters

**Parameter**-**Type	Default**	-**Description**

date:
YYYY-MM-DD	   
today -The date of the APOD image to retrieve


start_date:
YYYY-MM-DD	   
none	-The start of a date range, when requesting date for a range of dates. Cannot be used with date.


end_date:
YYYY-MM-DD	   
today -The end of the date range, when used with start_date.


api_key	string:
DEMO_KEY	  
api.nasa.gov key for expanded usage


**Example of Reponse code for the date 07/12/2014:**
`...

  {
    "date": "2014-07-12",
    
    "explanation": "A new star, likely the brightest supernova in recorded human history, lit up planet Earth's sky in the year 1006 AD. The expanding debris cloud from the stellar explosion, found in the southerly constellation of Lupus, still puts on a cosmic light show across the electromagnetic spectrum. In fact, this composite view includes X-ray data in blue from the Chandra Observatory, optical data in yellowish hues, and radio image data in red. Now known as the SN 1006 supernova remnant, the debris cloud appears to be about 60 light-years across and is understood to represent the remains of a white dwarf star. Part of a binary star system, the compact white dwarf gradually captured material from its companion star. The buildup in mass finally triggered a thermonuclear explosion that destroyed the dwarf star. Because the distance to the supernova remnant is about 7,000 light-years, that explosion actually happened 7,000 years before the light reached Earth in 1006. Shockwaves in the remnant accelerate particles to extreme energies and are thought to be a source of the mysterious cosmic rays.",
    
    "hdurl": "https://apod.nasa.gov/apod/image/1407/sn1006c.jpg",
    
    "media_type": "image",
    
    "service_version": "v1",
    
    "title": "SN 1006 Supernova Remnant",
    
    "url": "https://apod.nasa.gov/apod/image/1407/sn1006c_c800.jpg"
  },
...
`
