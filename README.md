# Campus API (JAX-RS) - README

## 1. Overview of API Design

This project is a RESTful Campus Management API built using Java JAX-RS with an in-memory datastore. The system manages:

* Rooms (physical areas that contain sensors)
* Sensors (devices that are assigned to the rooms)
* Sensor readings (the data that's in the sensors)

### Features of the project

* REST design using HTTP verbs like: GET, POST, DELETE
* API base path is versioned: `/api/v1`
* contains an in memory storage with `ConcurrentHashMap` (located in DataStore)
* Sub-resource locators for nested resources (`/sensors/{id}/readings`)
* Centralised exception handling via `ExceptionMapper` (located in exception mapper package)
* Request/response logging via JAX-RS filters

---

## 2. Project Running Instructions

### Requirements

* Java 8 or higher
* Maven installed
* A suitable IDE for java applications (IntelliJ / Eclipse recommended)

---

### Step 1: Clone the Repository

```bash
git clone https://github.com/Aiman8202/Client-Server-Architectures-Coursework
cd Client-Server-Architectures-Coursework
```

---

### Step 2: Build the Project

```bash
mvn clean install
```

---

### Step 3: Run the Server

Depending on setup:

#### Option A (Embedded Server)

```bash
mvn jetty:run
```

#### Option B (Main Class)

Run:

```
com.aiman.campus.MainApplication
```

---

### Access API

the base URL (used for the postman tests):

```
http://localhost:8080/api/v1
```

---

## 3. Sample cURL Commands

### 1. Create a Room

```bash
curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":1,"name":"Lab A","location":"Building 1"}'
```

---

### 2. Get All Rooms

```bash
curl -X GET http://localhost:8080/api/v1/rooms
```

---

### 3. Create a Sensor

```bash
curl -X POST http://localhost:8080/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":10,"type":"CO2","status":"ACTIVE","roomId":1,"currentValue":0}'
```

---

### 4. Filter Sensors by Type

```bash
curl -X GET "http://localhost:8080/api/v1/sensors?type=CO2"
```

---

### 5. Add Sensor Reading

```bash
curl -X POST http://localhost:8080/api/v1/sensors/10/readings \
-H "Content-Type: application/json" \
-d '{"id":1,"value":45.5,"timestamp":1713600000}'
```

---

### 6. Delete a Room

```bash
curl -X DELETE http://localhost:8080/api/v1/rooms/1
```

---

## **4. Coursework Question Answers**

### **Part 1**

1. JAX-RS resource classes require a new instance being created for each HTTP request,  this avoids requests being in a shared state which can avoid conflicts, however this does use more storage, so any persistent application data cannot be stored inside the resource class itself and must be instead stored externally, like an in memory datastore. A singleton can run the risk of having race conditions. This is where simultaneous modifications to data will likely make it corrupt. To prevent this, a proper synchronisation strategy must be used. 

2. HATEOAS allows APIs to return navigable links within responses. This allows the API to be found on its own which means you don’t need any external documentation which greatly helps with client adaptivity. Another benefit is that clients do not need to know the full structure of the API in advance even if the endpoints are changed as the server can update the links without breaking the client logic.

---

### **Part 2**

1. Returning only IDs reduces the payload size and network bandwidth but needs the client to send more requests. Returning full objects improves the usability of the server but increases response size, which therefore can increase network bandwidth.

2. DELETE is idempotent because multiple identical requests should produce the same result. If a room is already deleted, any further DELETE requests should still return a consistent response (like 404 or 204).

---

### **Part 3**

1. If a client sends text/plain or application/xml instead of application/json, JAX-RS will reject the request with 415 Unsupported Media Type. This is because JAX-RS will not find a compatible message body reader capable of converting that media type into the expected Java object. 

2. Query parameters are better for filtering collections because they are optional and more flexible as you do not need to change the base URL structure. Path parameters are better for identifying specific resources but using query parameters avoids any confusion between endpoints.

---

### **Part 4**

1. Sub-resource locators improve segmentation by assigning nested resources like the sensor readings to separate classes. This reduces complexity in large controllers and improves maintainability of the API.


---

### **Part 5:**

1. HTTP 422 is more accurate than 404 Not Found in this scenario because the request itself is well-formed and syntactically valid, but contains logically incorrect data. In this case the client sends a valid JSON payload but the roomId inside the payload refers to a resource that does not exist, so the error is not with the endpoint, instead is in the content of the request body itself.

2. Exposing internal Java stack traces to API clients poses significant cybersecurity risks because it reveals detailed information about the internal structure and behaviour of the application. This can lead to potential malicious users identifying weak points and can allow them to bypass validation logic, or trigger specific failure conditions that can crash the entire system.

3. Using JAX-RS filters for cross-cutting concerns like logging is advantageous because they provide a reusable mechanism to handle logic that applies to all requests and responses. However manually inserting Logger.info() statements in every resource can lead to poor maintainability as multiple files need to be modified for changing logging data.
