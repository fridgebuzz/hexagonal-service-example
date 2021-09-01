# Parking Rate Application

## Building and running as a .jar file

### Requirements

To build the application requires:

- Java 11
- Maven 3

### Building with Maven

To build a .jar file with Maven, from the project directory, use

`mvn clean install`

This will run all tests, including integration tests. To skip these, use:

`mvn clean install -DskipTests`
 
## Running as a .jar file

To run the resulting service locally, use:

`java -Dparking.rate.location="<location-of-startup-rates>.json" "-jar <path-to-jar-file>/<jar-file-name>.jar`

For example, if running from the project directory:

`java -Dparking.rate.location="src/main/resources/rates.json" -jar target/parking-service-1.0.0.jar`

If no location is provided for an initial file of rates, it will be loaded from `src/main/resources/rates.json` if possible.

## Running with Docker

### Additional Requirements 

- Docker

### Building the image

First build the .jar file as above. Then (from the project directory) build a docker image using:

`docker build -t <repo/image-name:tag> .`

For example:

`docker build -t test/parking:latest .`

### Running the container

To run the built container use:

`docker run -d --name <container-name> -p 5000:5000 <repo/image-name:tag>`

For example:

`docker run -d --name parking -p 5000:5000 test/parking:latest`

Both of these examples will use the initial rates.json file in `src/main/resources`. It can be replaced using the API.

# API Endpoints
              
## Base path

All endpoints are relative to:

`http://localhost:5000/parking`

## Rates

There are two endpoints for rates.
                
```
GET /rates
```

returns a JSON representation of current rates, for example:

```
{
	"rates": [
		{
			"days" : "mon,tues,thurs",
			"times" : "0900-2100",
			"tz" : "America/Chicago",
			"price" : 1500
		},
		{
			"days" : "fri,sat,sun",
			"times" : "0900-2100",
			"tz" : "America/Chicago",
			"price" : 2000
		},
		{
			"days" : "wed",
			"times" : "0600-1800",
			"tz" : "America/Chicago",
			"price" : 1750
		},
		{
			"days" : "mon,wed,sat",
			"times" : "0100-0500",
			"tz" : "America/Chicago",
			"price" : 1000
		},
		{
			"days" : "sun,tues",
			"times" : "0100-0700",
			"tz" : "America/Chicago",
			"price" : 925
		}
	]
}
```

To replace existing rates, PUT to the same resource:

```
PUT /rates
Content-type: application/json

{
	"rates": [
		{
			"days" : "mon,tues,thurs",
			"times" : "0900-2200",
			"tz" : "America/New_York",
			"price" : 1500
		},
		{
			"days" : "fri,sat,sun",
			"times" : "0900-2200",
			"tz" : "America/New_York",
			"price" : 2100
		},
		{
			"days" : "wed",
			"times" : "0600-1800",
			"tz" : "America/Chicago",
			"price" : 1750
		},
		{
			"days" : "mon,wed,sat",
			"times" : "0100-0500",
			"tz" : "America/Chicago",
			"price" : 750
		},
		{
			"days" : "sun,tues",
			"times" : "0100-0700",
			"tz" : "America/Chicago",
			"price" : 900
		}
	]
}
```
## Prices

To find a price for parking:

```
GET /price?start=<ISO-8601 date/time with offset>&end=<ISO-8601 date/time with offset>
```

For example:

```
GET /price?start=2015-07-01T07:00:00-05:00&end=015-07-01T12:00:00-05:00
```
 
If a price is found, it is returned as JSON

```
{'price': 1750}
```
  
If a price cannot be determined, the following is returned:

`unavailable`

### Encoding of time offsets

Since `+` in a URL (including a query parameter) will be turned into a space and result in a `400 Bad Request` result, 
characters must be percent-encoded as `%2B`.

For example:

`2015-07-04T20:00:00+05:00` must be encoded to `2015-07-04T20:00:00%2B05:00`

### Conditions for matching or failing to match a price

- If a given date/time range spans more than one day, the price will be unavailable
- If more than one price would match, the price will be unavailable
- Otherwise, if a given date/time range would be completely encompassed by one of the rates, it will be returned. This
is based on date/times representing the same instant in time once the offset is applied. 

## Metrics

Prometheus-style metrics are available at:

`GET actuator/prometheus`

In addition to a set of default JVM metrics, we can find count and timing information for endpoint requests. For example:

- `http_server_requests_seconds_count` is the total number of requests the application received at this endpoint
- `http_server_requests_seconds_sum` is the sum of the duration of every request the application received at this endpoint

Example output for the prometheus metrics endpoint itself look like this:

```
http_server_requests_seconds_count{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/actuator/prometheus",} 3.0
http_server_requests_seconds_sum{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/actuator/prometheus",} 0.047001569
```

Tags show:
- the exception, if any
- the method (GET, PUT, etc.)
- the outcome
- the returned HTTP status code
- the URI

In conjunction with Prometheus (and Grafana for visualization) you can use the tags to view only the metrics you wish (such as the number of errors, etc.) 
