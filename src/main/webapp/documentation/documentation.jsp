<%-- 
    Document   : documentation
    Created on : 08-09-2016, 11:59:14
    Author     : xboxm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CA1-Documentation</title>
    </head>
    <body>
        <h1>Documentation page for chat project</h1>
        <form action="../index.html">
            <input type="submit" value="back">
        </form>
        <p>link to github source code: <a href="https://www.github.com/konggrethe/ca1-chatproject">Click here</a></p>
        <p>For a walkthrough of who contribruted and with what <a href="CA1-Noter.txt">Click here</a></p> <br>
        <h1>Tests:</h1>
        <div>Test results: <br> <img src="test1.png"/></div>
        <div>Test 1: <br> <img src="test2.PNG"/></div>
        <div>Test 2: <br> <img src="test3.PNG"/></div>
        <div>Test 3: <br> <img src="test4.PNG"/></div>
        <br>
        <h1>Design:</h1>
        <div>Diagram 1: <br> <img src="diagram1.jpg"/></div>
		<p>A user connects through a socket to the server. On the server, a thread is spawned to take care of input and output to the user. Such a thread can take the input from one user and turn it into output to everyone else.
Input is treated differently based on what it contains. The text LOGIN assigns the user a temporary name. MSG indicates a message that can be to anyone, and LOGOUT disconnects the user as well as informing everyone else that the list of users changed.
By keeping track of a clientlist, the server can figure out how to forward a message to only certain users.<p>
<br>
        <div>Diagram 2: <br> <img src="diagram2.jpg"/></div>
        <p>This diagram describes that multible clients can connect to a server 
           and each of these clients gets their own ClientHandler which then will 
           process the input/out streams, which means it process what is being send and received.</p><br>
        <div>Diagram 3: <br> <img src="diagram3.jpg"/></div>
        <p>This diagram describes more specificly what happens in Diagram 2.
A client connects to the server where the client then gets his own ClientHandler 
for processing input/output streams. This client then gets added with a ClientThread 
which makes sure that only he can use this Thread and that he can write, via our protocol, 
to other clients with another unique Thread added to them.</p><br>
        <form action="../index.html">
            <input type="submit" value="back">
        </form>
		
    </body>
</html>
