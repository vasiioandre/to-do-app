<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<html>
	<head> List To Do's Page for ${name}</head>
	<body>
	<h1> List To Do's Page</h1>
	<table>
		<thread>
			<tr>
				<th>Description</th>
				<th>Target Date</th>
				<th>Is Done?</th>
			</tr>
		</thread>
		<tbody>
			<c:forEach items="${todos}" var="todo">
				<tr>
					<td>${todo.description}</td>
					<td>${todo.targetDate}</td>
					<td>${todo.done}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</body>
</html>