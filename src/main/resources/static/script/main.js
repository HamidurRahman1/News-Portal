
var LOGIN_USERNAME_LOC = "loginUsername";
var LOGIN_PASSWORD_LOC = "loginPassword";

var AUTH_USERNAME_LOC = "p-username";
var AUTH_PASSWORD_LOC = "p-password";

var SIGN_UP_FN_LOC = "signUpFirstName";
var SIGN_UP_LN_LOC = "signUpLastName";
var SIGN_UP_USERNAME_LOC = "signUpUsername";
var SIGN_UP_PASSWORD_LOC = "signUpPassword";

var COMMENTS_BY_ARTICLE_ID_LOC = "commentsByArticleId";
var DELETE_USER_BY_ID_LOC = "deleteUserById";
var DELETE_COMMENT_BY_COMMENT_ID_LOC = "deleteCommentById";
var AUTHOR_ID_FOR_ARTICLES_LOC = "authorIdForArticles";

var INVALID_INPUT_IN_ID = "Invalid input entered in the id field=";
var AUTHOR_ID_FOR_INFO_LOC = "authorIdForInfo";
var BODY_CONTAINS_LOC = "bodyContains";
var RESULTS_LOC = "results";


function p_loadArticles(key)
{
    var request = new XMLHttpRequest();
    if(key === 1) request.open("GET", "http://localhost:8080/blogs/api/v1/public/articles");
    else request.open("GET", "http://localhost:8080/blogs/api/v1/public/articles/no-author");
    request.send();
    request.onload = function()
    {
        if(request.status === 200)
        {
            var json_data = JSON.parse((request.response));
            var table = document.createElement('table');

            for (var i in json_data){
                var tr = document.createElement('tr');

                var td1 = document.createElement('td');
                var td2 = document.createElement('td');
                var td3 = document.createElement('td');
                var td4 = document.createElement('td');
                var td5 = document.createElement('td');

                var text1 = document.createTextNode(json_data[i]['articleId']);
                var text2 = document.createTextNode(json_data[i]['title']);
                var text3 = document.createTextNode(json_data[i]['body']);
                var text4 = document.createTextNode(timestampToDateAMPM(json_data[i]['timestamp']));
                var text5 = document.createTextNode(json_data[i]['published']);

                td1.appendChild(text1);
                td2.appendChild(text2);
                td3.appendChild(text3);
                td4.appendChild(text4);
                td5.appendChild(text5);
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);

                table.appendChild(tr);
            }
            document.getElementById(RESULTS_LOC).innerHTML = "";
            document.getElementById(RESULTS_LOC).appendChild(table);
        }
        else
        {
            if(key === 1) alert("failed to load all articles");
            else alert("failed to load all articles with no author");
        }
    }
}

function p_loadAllComments()
{
    var request = new XMLHttpRequest();
    request.open("GET", "http://localhost:8080/blogs/api/v1/public/comments");
    request.send();
    request.onload = function()
    {
        if(request.status === 200)
        {
            var json_data = JSON.parse((request.response));
            var table = document.createElement('table');

            for (var i in json_data){
                var tr = document.createElement('tr');

                var td1 = document.createElement('td');
                var td2 = document.createElement('td');

                var text1 = document.createTextNode(json_data[i]['commentId']);
                var text2 = document.createTextNode(json_data[i]['comment']);

                td1.appendChild(text1);
                td2.appendChild(text2);
                tr.appendChild(td1);
                tr.appendChild(td2);

                table.appendChild(tr);
            }
            document.getElementById(RESULTS_LOC).innerHTML = "";
            document.getElementById(RESULTS_LOC).appendChild(table);
        }
        else alert("failed to load all articles");
    }
}

function p_loadCommentsByArticleId()
{
    var articleId = document.getElementById(COMMENTS_BY_ARTICLE_ID_LOC).value.trim();
    if(!validateIdField(articleId))
    {
        alert(INVALID_INPUT_IN_ID + articleId);
        return;
    }
    var request = new XMLHttpRequest();
    request.open("GET", "http://localhost:8080/blogs/api/v1/public/article/"+ articleId + "/comments");
    request.send();
    request.onload = function()
    {
        if(request.status === 200)
        {
            var json_data = JSON.parse((request.response));
            var table = document.createElement('table');

            for (var i in json_data)
            {
                var tr = document.createElement('tr');

                var td1 = document.createElement('td');
                var td2 = document.createElement('td');

                var text1 = document.createTextNode(json_data[i]['commentId']);
                var text2 = document.createTextNode(json_data[i]['comment']);

                td1.appendChild(text1);
                td2.appendChild(text2);
                tr.appendChild(td1);
                tr.appendChild(td2);

                table.appendChild(tr);
            }
            document.getElementById(RESULTS_LOC).innerHTML = "";
            document.getElementById(RESULTS_LOC).appendChild(table);
        }
        else alert("failed to load comments with articleId="+articleId+"\n"+request.response.toString());
    }
}

function p_doLogin()
{
    var fields = validateLoginFields(LOGIN_USERNAME_LOC, LOGIN_PASSWORD_LOC);
    if(fields === false) return;
    var data = {
        "username": fields[0],
        "password": fields[1]
    };

    var request = new XMLHttpRequest();
    request.open("POST", "http://localhost:8080/blogs/api/v1/public/login", true);
    request.setRequestHeader('Content-Type', 'application/json');
    request.setRequestHeader('Accept', 'application/json');
    request.send(JSON.stringify(data));
    request.onload = function ()
    {
        if(request.status === 200) alert("login was success");
        else alert(request.response.toString());
    }
}

function p_doSignUp()
{
    var allFields = validateSignUp();
    if(allFields !== false)
    {
        var data = {
            "firstName": allFields[0],
            "lastName": allFields[1],
            "username": allFields[2],
            "password": allFields[3]
        };

        var request = new XMLHttpRequest();
        request.open("POST", "http://localhost:8080/blogs/api/v1/public/user/signup", true);
        request.setRequestHeader('Content-Type', 'application/json');
        request.setRequestHeader('Accept', 'application/json');
        request.send(JSON.stringify(data));
        request.onload = function ()
        {
            if(request.status === 200) alert("sign up was success.\n"+request.response.toString());
            else alert(request.response.toString());
        }
    }
}

function r_getAuthors()
{
    var fields = validateLoginFields(AUTH_USERNAME_LOC, AUTH_PASSWORD_LOC);
    if(fields !== false)
    {
        var url = "http://localhost:8080/blogs/api/v1/r/authors";
        var request = new XMLHttpRequest();

        request.open("GET", url, true);
        request.setRequestHeader("Content-type", "application/json");
        request.setRequestHeader("Authorization", "Basic " + btoa(fields[0] + ":" + fields[1]));
        request.send();
        request.onload = function ()
        {
            if (request.readyState === request.DONE)
            {
                var response = request.responseText;
                var obj = JSON.parse(response);
                console.log(response.toString());
            }
            else alert(request.response.toString());
        };
    }
}

function r_bodyContains()
{
    var fields = validateLoginFields(AUTH_USERNAME_LOC, AUTH_PASSWORD_LOC);
    if(fields !== false)
    {
        var queryString = document.getElementById(BODY_CONTAINS_LOC).value;

        if(queryString.length < 1 || !queryString.match(/^[A-Za-z0-9]+$/))
        {
            alert("string must be alphanumeric characters");
            return;
        }
        else
        {
            var url = "http://localhost:8080/blogs/api/v1/r/articles/text?bodyContains="+queryString;
            var request = new XMLHttpRequest();

            request.open("GET", url, true);
            request.setRequestHeader("Content-type", "application/json");
            request.setRequestHeader("Authorization", "Basic " + btoa(fields[0] + ":" + fields[1]));
            request.send();
            request.onload = function ()
            {
                if (request.readyState === request.DONE)
                {
                    var response = request.responseText;
                    var obj = JSON.parse(response);
                    console.log(response.toString());
                    alert(response.toString());
                }
                else alert(request.response.toString());
            }
        }
    }
}

function r_authorInfoByAuthorId()
{
    var fields = validateLoginFields(AUTH_USERNAME_LOC, AUTH_PASSWORD_LOC);
    if(fields !== false)
    {
        var authorId = document.getElementById(AUTHOR_ID_FOR_INFO_LOC).value.trim();
        if(!validateIdField(authorId))
        {
            alert(INVALID_INPUT_IN_ID + authorId);
            return;
        }
        var request = new XMLHttpRequest();
        request.open("GET", "http://localhost:8080/blogs/api/v1/r/author/"+ authorId);
        request.setRequestHeader("Content-type", "application/json");
        request.setRequestHeader("Authorization", "Basic " + btoa(fields[0] + ":" + fields[1]));
        request.send();
        request.onload = function()
        {
            if(request.status === 200)
            {
                var json_data = JSON.parse((request.response));

                var table = document.createElement('table');

                var tr = document.createElement('tr');

                var td1 = document.createElement('td');
                var td2 = document.createElement('td');
                var td3 = document.createElement('td');
                var td4 = document.createElement('td');

                var text1 = document.createTextNode(json_data['userId']);
                var text2 = document.createTextNode(json_data['firstName']);
                var text3 = document.createTextNode(json_data['lastName']);
                var text4 = document.createTextNode(json_data['enabled']);

                td1.appendChild(text1);
                td2.appendChild(text2);
                td3.appendChild(text3);
                td4.appendChild(text4);
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);

                table.appendChild(tr);
                document.getElementById(RESULTS_LOC).innerHTML = "";
                document.getElementById(RESULTS_LOC).appendChild(table);

                var rolesTable = getRolesTable(json_data['roles']);
                document.getElementById(RESULTS_LOC).appendChild(document.createElement("p"));
                document.getElementById(RESULTS_LOC).appendChild(rolesTable);
                document.getElementById(RESULTS_LOC).appendChild(document.createElement("p"));
                var articleTable = tableForArticles(json_data["articles"]);
                if(articleTable !== undefined) document.getElementById(RESULTS_LOC).appendChild(articleTable);
            }
            else alert(request.response.toString());
        }
    }
}

function r_authorArticlesByAuthorId()
{
    var fields = validateLoginFields(AUTH_USERNAME_LOC, AUTH_PASSWORD_LOC);
    if(fields !== false)
    {
        var authorId = document.getElementById(AUTHOR_ID_FOR_ARTICLES_LOC).value.trim();
        if(!validateIdField(authorId))
        {
            alert(INVALID_INPUT_IN_ID + authorId);
            return;
        }
        var request = new XMLHttpRequest();
        request.open("GET", "http://localhost:8080/blogs/api/v1/r/author/"+ authorId +"/articles");
        request.setRequestHeader("Content-type", "application/json");
        request.setRequestHeader("Authorization", "Basic " + btoa(fields[0] + ":" + fields[1]));
        request.send();
        request.onload = function()
        {
            if(request.status === 200)
            {
                var json_data = JSON.parse((request.response));

                var table = tableForArticles(json_data);

                document.getElementById(RESULTS_LOC).innerHTML = "";
                document.getElementById(RESULTS_LOC).appendChild(table);
            }
            else alert(request.response.toString());
        }
    }
}

function r_deleteCommentByCommentId()
{
    var fields = validateLoginFields(AUTH_USERNAME_LOC, AUTH_PASSWORD_LOC);
    if(fields !== false)
    {
        var commentId = document.getElementById(DELETE_COMMENT_BY_COMMENT_ID_LOC).value.trim();
        if(!validateIdField(commentId))
        {
            alert(INVALID_INPUT_IN_ID + commentId);
            return;
        }
        var request = new XMLHttpRequest();
        request.open("DELETE", "http://localhost:8080/blogs/api/v1/r/delete/comment/"+ commentId);
        request.setRequestHeader("Content-type", "application/json");
        request.setRequestHeader("Authorization", "Basic " + btoa(fields[0] + ":" + fields[1]));
        request.send();
        request.onload = function()
        {
            if(request.status === 200) alert("comment with id="+commentId+" has successfully been deleted.");
            else alert(request.response.toString());
        }
    }
}

function r_deleteUserByUserId()
{
    var fields = validateLoginFields(AUTH_USERNAME_LOC, AUTH_PASSWORD_LOC);
    if(fields !== false)
    {
        var userId = document.getElementById(DELETE_USER_BY_ID_LOC).value.trim();
        if(!validateIdField(userId))
        {
            alert(INVALID_INPUT_IN_ID + userId);
            return;
        }
        var request = new XMLHttpRequest();
        request.open("DELETE", "http://localhost:8080/blogs/api/v1/r/delete/user/"+ userId);
        request.setRequestHeader("Content-type", "application/json");
        request.setRequestHeader("Authorization", "Basic " + btoa(fields[0] + ":" + fields[1]));
        request.send();
        request.onload = function()
        {
            if(request.status === 200) alert("User with id="+userId+" has successfully been deleted.");
            else alert(request.response.toString());
        }
    }
}

function getRolesTable(jsonRoles)
{
    var table = document.createElement('table');

    for (var i in jsonRoles)
    {
        var tr = document.createElement('tr');

        var td1 = document.createElement('td');
        var td2 = document.createElement('td');

        var text1 = document.createTextNode(jsonRoles[i]['roleId']);
        var text2 = document.createTextNode(jsonRoles[i]['role']);

        td1.appendChild(text1);
        td2.appendChild(text2);
        tr.appendChild(td1);
        tr.appendChild(td2);

        table.appendChild(tr);
    }
    return table;
}

function tableForArticles(jsonArticles)
{
    if(jsonArticles.length !== 0)
    {
        var table = document.createElement('table');

        for (var i in jsonArticles)
        {
            var tr = document.createElement('tr');

            var td1 = document.createElement('td');
            var td2 = document.createElement('td');
            var td3 = document.createElement('td');
            var td4 = document.createElement('td');
            var td5 = document.createElement('td');

            var text1 = document.createTextNode(jsonArticles[i]['articleId']);
            var text2 = document.createTextNode(jsonArticles[i]['title']);
            var text3 = document.createTextNode(jsonArticles[i]['body']);
            var text4 = document.createTextNode(timestampToDateAMPM(jsonArticles[i]['timestamp']));
            var text5 = document.createTextNode(jsonArticles[i]['published']);

            td1.appendChild(text1);
            td2.appendChild(text2);
            td3.appendChild(text3);
            td4.appendChild(text4);
            td5.appendChild(text5);
            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);

            table.appendChild(tr);
        }
        return table;
    }
}

function timestampToDateAMPM(timestamp)
{
    var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
    var date = new Date(parseInt(timestamp) * 1000);
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12;
    minutes = minutes < 10 ? '0' + minutes : minutes;
    return months[date.getMonth()] + " " + date.getDay() + ", " + hours + ':' + minutes + ':' + date.getSeconds() + ' ' + ampm;
}

function validateIdField(value)
{
    if (value.toString().trim().length < 1 || !value.match(/^[0-9]+$/)) return false;
    return true;
}

function validateLoginFields(usernameLoc, passwordLoc)
{
    var username = document.getElementById(usernameLoc).value.trim();
    var password = document.getElementById(passwordLoc).value.trim();
    if(username.length < 5 || password.length < 5)
    {
        alert("please use right credentials to make the call");
        return false;
    }
    return [username, password];
}

function validateSignUp()
{
    var firstName = document.getElementById(SIGN_UP_FN_LOC).value.trim();
    var lastName = document.getElementById(SIGN_UP_LN_LOC).value.trim();
    var username = document.getElementById(SIGN_UP_USERNAME_LOC).value.trim();
    var password = document.getElementById(SIGN_UP_PASSWORD_LOC).value.trim();
    if(firstName.length < 2 || lastName.length < 2)
    {
        alert("first name, last name must have length of 2-45");
        return false;
    }
    if(username.length < 5 || password.length < 5)
    {
        alert("username, password must have length of 5-20, 5-15");
        return false;
    }
    return [firstName, lastName, username, password];
}