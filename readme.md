# CRM RESTFUL API

### Logging in

When either a user or admin is logged against the API, a token is sent, 
so they can perform operations with it.

Queries must be issued with POST method. Data must be sent in JSON format. Server
will respond in JSON format also.

A client would normally issue to ``<server>:port/login``
```
{
	"username" : "admin",
	"password" : "admin"
}
```

Server will respond whether the credentials are correct:
````
{
    "code": 200,
    "description": "OK",
    "token": "-57440681410-1086-6-120-29-84-5-104-69-70-22-20870"
}
````

Or will respond this if the authentication is incorrect.
```
{
    "code": 401,
    "description": "UNAUTHORIZED: tried to operate on a protected resource without providing the proper authentication credentials",
    "token": null
}
```

#### Managing users

A user is an entity who can modify another entity called customer. The same authentication system is used.
When a user is authenticated a token is sent.

##### Creating a customer [``<server>:port/api/customers/add``]

```
{
	"token" : "-1086447-117-105-35-3949-10743-395875-661038734-3615-109",
	"customer" : {
		"name" : "James",
		"surname" : "Morrison"
	}
}
```

Response,

```
{
    "code": 200,
    "description": "OK",
    "token": null
}
```

##### Retrieving customers [``<server>:port/api/customers``]

```
{
	"token" : "-1086447-117-105-35-3949-10743-395875-661038734-3615-109"
}
```

Response,

```
[
    {
        "id": 3,
        "photoURL": null,
        "name": "James",
        "surname": "Morrison",
        "last_person_who_modified": "admin"
    }
]
```

Note that a user whose role was **ADMIN** could also perform operations on customers.

##### Updating customers [``<server>:port/api/customers/update``]

```
{
	"token" : "-1086447-117-105-35-3949-10743-395875-661038734-3615-109",
	"customer" : {
		"id" : "3",
		"name" : "Thomas"
	}
}
```

Also, a photo can be uploaded given a customer id. I have created this form
for easing the process (see .jsp file within webapp directory)

![uploadingapiccustomer](https://user-images.githubusercontent.com/19231158/40273378-9a8fbd18-5bbf-11e8-86b5-b118d30e62f6.PNG)

After uploading,

```
[
    {
        "id": 3,
        "photoURL": "C:\\Users\\Public\\Pictures\\3_Capture.PNG",
        "name": "Thomas",
        "surname": "Morrison",
        "last_person_who_modified": "admin"
    }
]
```

##### Deleting a customer [``<server>:port/api/customers/remove``]

```
{
	"token" : "-1086447-117-105-35-3949-10743-395875-661038734-3615-109",
	"customer" : {
		"id" : "1"
	}
}
```

If the token were incorrect response would be,

```
{
    "code": 403,
    "description": "FORBIDDEN",
    "token": null
}
```

##### Retrieving a particular customer [``<server>:port/api/customers/get``]

```
{
	"token" : "-1086447-117-105-35-3949-10743-395875-661038734-3615-109",
	"customer" : {
		"id" : "3"
	}
}
```

Response,

```
{
    "code": 200,
    "description": "OK",
    "token": null,
    "customer": {
        "id": 3,
        "photoURL": "C:\\Users\\Public\\Pictures\\3_Capture.PNG",
        "name": "Thomas",
        "surname": "Morrison",
        "last_person_who_modified": "admin"
    }
}
```





