<h1>Hello World</h1>
<h2>Spring MVC file upload example</h2>

<form method="POST" action="upload"
      enctype="multipart/form-data">
    <br>
    <label>or, CustomerID
        <input type="text" name="customerId"/>
    </label>
    <br>
    <label>Token
        <input type="text" name="token"/>
    </label>
    <br>
    Please select a file to upload :<br>
    <input type="file" name="file" />
    <br>
    <input type="submit" value="upload" />
</form>