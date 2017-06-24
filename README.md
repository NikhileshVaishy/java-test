# Trias-Parser Test

As a part of SevenRE recruitment process, candidates need to demonstrate their coding skills. For this they would need to extend current project to fulfill given tasks.

Idea is to create RESTApi to provide parsed data from public transport domain. TRIAS is an open system which provides data regarding stops/trips of Germany. In general the task is to create API to provide stops & trips details in `JSON` format.

Basic project is provided as a framework and is recommanded. However one is free to use different one. `TriasService` provides two meethod needed for this test project.

## Tasks
Here goes the tasks as a list:
* Create a Stop table using `JPA/Hibernate`
* **Every 2 hours** stop database is to be synced with *stops result* from Trias. To acheive this the following is to be done
    * Given a latitude/longitude **bounding box** make a 40x40 grid to discretize the bounding box. ![N|Solid](http://i.imgur.com/FEjZw6F.png)
    * Call TRIAS for all those 1.6k lat/log and get closest stops to those positions. Use `TriasService.stopLocRequestByGeo()`
    * Parse response from `TriasService` and map it to `Stop` class. [Xml parsing tips](#xml-parsing-tips)
    * Merge response from 1.6k calls into one, identify **unique stops** and store them in database
    * Stops in database should be unique based on *GlobalId* provided by TRIAS
    * Test bounding box: (latitude, longitude) `(49.370416, 8.137682)` -> `(49.686029, 8.567396)`

* Create REST-API to fetch stops & trips. The endpoints will be
    * **Search stop by stopName**: Use stops from database and return top 10 stop which are matching query. Sample: `/ws/trias/stopName?q=pirma`
    * **Search stop by locName**: Use stops from database and return top 25 stop which are matching query. Sample `/ws/trias/locName?q=pirma`
    * **Search Trip by stopId**: from TRIAS (Given globalId and time get trips from stop). Sample `/ws/trias/trip?stopId=de:7312:91312`
* Write basic unit-tests for the above REST api


## Bonus points
* Use JavaRx & non-blocking code
* Use of java lambda’s
* Swagger documentation of REST api
* Add JWT to secure your REST api

## Tips
#### Xml Parsing
* Use xpath to parse XML response from Trias
* Use some online [xpath tester](https://www.freeformatter.com/xpath-tester.html) to test xpath queries (in response remove all attributes from `<Trias>`    tag so online tester can use it)
* Demo query for stop name 


#### TRIAS
`TriasService` provides response from Trias. But to test xpath it would be easy to use two sample in postman and see results, write xpath query, etc. Use **POST** Http-method to fetch response.

> TRIAS Endpoint: `http://trias.vrn.de:8080/Middleware/Data/trias`

#### Near By stops
Given a pair of latitude and longtitude, a sample post request can be created like below
```xml
<Trias xmlns="trias" xmlns:siri="http://www.siri.org.uk/siri" version="1.0">
<ServiceRequest>
  <RequestPayload>
    <StopEventRequest>
      <Location>
        <LocationRef>
          <GeoPosition>
            <Latitude>49.425793</Latitude>
            <Longitude>7.746275</Longitude>
          </GeoPosition>
        </LocationRef>
      </Location>
      <Restrictions>
        <Type>stop</Type>
        <Language>de</Language>
        <NumberOfResults>3</NumberOfResults>
        <StopEventType>departure</StopEventType>
      </Restrictions>
    </StopEventRequest>
  </RequestPayload>
</ServiceRequest>
</Trias>
```
#### Trips from stop
Given a sample stopId, a sample POST request can be made as below
```xml
<Trias xmlns="trias" xmlns:siri="http://www.siri.org.uk/siri" version="1.0">
<ServiceRequest>
  <RequestPayload>
    <StopEventRequest>
      <Location>
        <LocationRef>
          <StopPointRef>de:7312:91312</StopPointRef>
          <LocationName>
            <Text></Text>
          </LocationName>
        </LocationRef>
      </Location>
      <Restrictions>
        <Type>stop</Type>
        <Language>de</Language>
        <NumberOfResults>3</NumberOfResults>
        <StopEventType>departure</StopEventType>
      </Restrictions>
    </StopEventRequest>
  </RequestPayload>
</ServiceRequest>
</Trias>
```

### Reference
* https://spring.io/guides/tutorials/bookmarks/
* https://docs.spring.io/spring-security/site/docs/current/reference/html/test-mockmvc.html
