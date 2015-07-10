<%@page import="java.io.*,java.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.google.appengine.api.datastore.*"%>

<html>
<head>
<script>
	
<%String msg = request.getParameter("msg");%>
	if (msg != null || msg != "")
		alert(msg);
</script>
</head>
<body>

	<H3 align="center">Simple Library Management System using Google
		Datastore</H3>

	<h2></h2>

	<fieldset>
		<legend>Add Member</legend>
		<center>
			<form action="/lms" method="GET">
				Enter USN: <input type="text" name="USN" required autofocus> Enter Name: <input
					type="text" name="Name" required> <input type="Submit"
					Value="Add Member" name="btn">
			</form>
		</center>
	</fieldset>
	<br>
	<br>
	<fieldset>
		<legend>Add Book</legend>
		<center>
			<form action="/lms" method="GET">
				Enter Book Id: <input type="text" name="BookId" required autofocus> Enter
				Title: <input type="text" name="Title" required> <input type="Submit"
					Value="Add Book" name="btn">
			</form>
		</center>
	</fieldset>
	<br>
	<br>
	<fieldset>
		<legend>Issue Book</legend>
		<center>
			<form action="/lms" method="GET">
				Enter USN: <input type="text" name="USN" required autofocus> Enter Book Id: <input
					type="text" name="BookId" required> <input type="Submit"
					Value="Issue Book" name="btn">
			</form>
		</center>
	</fieldset>
	<br>
	<br>
	<fieldset>
		<legend>Return Book</legend>
		<center>
			<form action="/lms" method="GET">
				Enter USN: <input type="text" name="USN" required autofocus> Enter Book Id: <input
					type="text" name="BookId" required> <input type="Submit"
					Value="Return Book" name="btn">
			</form>
		</center>
	</fieldset>
	&nbsp;&nbsp;&nbsp;&nbsp;

	<table border="3" align="center">
		<tr>
			<th>List of Members</th>
			<th>List of Books</th>
			<th>Books Issued</th>
		</tr>
		<tr>
			<td><table border="1" align="center">
					<th>USN</th>
					<th>Name</th>

					<%
						DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
						Query q = new Query("Member");
						PreparedQuery pq = ds.prepare(q);

						for (Entity att : pq.asIterable()) {
							String usn = att.getProperty("USN").toString();
							String name = att.getProperty("Name").toString();
					%>

					<tr>
						<td><%=usn%></td>
						<td><%=name%></td>
					</tr>
					<%
						}
					%>
				</table></td>
			<td><table border="1" align="center">
					<th>Book ID</th>
					<th>Title</th>

					<%
						ds = DatastoreServiceFactory.getDatastoreService();
						q = new Query("Book");
						pq = ds.prepare(q);

						for (Entity att : pq.asIterable()) {
							String bookid = att.getProperty("BookId").toString();
							String title = att.getProperty("Title").toString();
					%>

					<tr>
						<td><%=bookid%></td>
						<td><%=title%></td>
					</tr>
					<%
						}
					%>

				</table></td>
			<td><table border="1" align="center">
					<th>Book ID</th>
					<th>USN</th>

					<%
						ds = DatastoreServiceFactory.getDatastoreService();
						q = new Query("Issue");
						pq = ds.prepare(q);

						for (Entity att : pq.asIterable()) {
							String usn = att.getProperty("USN").toString();
							String bookid = att.getProperty("BookId").toString();
					%>

					<tr>
						<td><%=usn%></td>
						<td><%=bookid%></td>
					</tr>
					<%
						}
					%>

				</table></td>
		</tr>
	</table>
</body>
</html>