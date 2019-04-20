<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Lab2</title>
    <link rel="stylesheet" type="text/css" href="index_styles.css"/>

    <script type="text/javascript" src="//code.jquery.com/jquery-2.1.0.js"></script>
    <script type="text/javascript" src="myscript.js"></script>

    <meta charset="UTF-8">
</head>

<body>
<header>
    <table width=100%>
        <tr>
            <td class="variant">Вариант: 20293</td>
            <td class="group">Группа: P3202</td>
        </tr>
        <tr>
            <td class="autor">Зайцев Артем Алексеевич</td>
            <td class="autor">Шестакова Софья Александровна</td>
        </tr>
    </table>
</header>

<table width="100%">
    <tr>
        <td width="30%">
            <div class="flex-container">
                <form action="auth" method="post">
                    <div>
                        <fieldset>
                            <ld>Username</ld>
                            <label>
                                <input id="username" class="parameter" type="text" name="username">
                            </label>
                        </fieldset>
                    </div>
                    <div>
                        <fieldset>
                            <legend>Password</legend>
                            <label>
                                <input id="password" class="parameter" type="text" name="password">
                            </label>
                        </fieldset>
                    </div>
                    <div>
                        <%
                            String errorMessage = (String) request.getSession().getAttribute("errorMessage");
                            if (errorMessage != null) {
                        %>
                        <%=errorMessage%> <%
                        };
                    %>
                    </div>
                    <div>
                        <input id="magic_button" class="parameter"
                               style="padding: 3%; margin-right: 2%; margin-top: 3%"
                               type="submit" value="Отправить">
                    </div>
                </form>
            </div>
        </td>
    </tr>
</table>

</body>
</html>

