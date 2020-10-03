
function loadArticles(key) {
    var r = new XMLHttpRequest();
    if(key === 1) r.open("GET", "http://localhost:8080/blogs/api/v1/public/articles");
    else r.open("GET", "http://localhost:8080/blogs/api/v1/public/articles/no-author");
    r.send();
    r.onload = function() {
        if(r.status === 200)
        {
            var json_data = JSON.parse((r.response));
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
                var text4 = document.createTextNode(json_data[i]['timestamp']);
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
            document.getElementById("results").innerHTML = "";
            document.getElementById("results").appendChild(table);
        }
        else{
            if(key === 1) alert("failed to load all articles");
            else alert("failed to load all articles with no author");
        }
    }
}

function loadAllComments() {
    var r = new XMLHttpRequest();
    r.open("GET", "http://localhost:8080/blogs/api/v1/public/comments");
    r.send();
    r.onload = function() {
        if(r.status === 200)
        {
            var json_data = JSON.parse((r.response));
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
            document.getElementById("results").innerHTML = "";
            document.getElementById("results").appendChild(table);
        }
        else{
            alert("failed to load all articles");
        }
    }
}

function validateIdField(value) {
    if (value.toString().trim().length < 1 || !value.match(/^[0-9]+$/)) return false;
    return true;
}

function loadCommentsByArticleId() {
    var articleId = document.getElementById("articleId").value.trim();
    if(!validateIdField(articleId))
    {
        alert("Invalid input entered in the id field=" + articleId);
        return;
    }
    var request = new XMLHttpRequest();
    request.open("GET", "http://localhost:8080/blogs/api/v1/public/article/"+ articleId + "/comments");
    request.send();
    request.onload = function() {
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
            document.getElementById("results").innerHTML = "";
            document.getElementById("results").appendChild(table);
        }
        else{
            var error = JSON.parse((request.response));
            alert("failed to load comments with articleId="+articleId+"\nMessage: "+error['errorMessage']);
        }
    }
}

function loadArticlesByAuthorId() {
    var fields = validateLoginFields();
    if(fields !== false)
    {
        var authorId = document.getElementById("authorId").value.trim();
        if(!validateIdField(authorId))
        {
            alert("Invalid input entered in the id field=" + authorId);
            return;
        }
        var request = new XMLHttpRequest();
        request.open("GET", "http://localhost:8080/blogs/api/v1/r/author/"+ authorId);
        request.setRequestHeader("Content-type", "application/json");
        request.setRequestHeader("Authorization", "Basic " + btoa(fields[0] + ":" + fields[1]));
        request.send();
        request.onload = function() {
            if(request.status === 200)
            {
                var json_data = JSON.parse((request.response));
                console.log(json_data);
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
                    var text4 = document.createTextNode(json_data[i]['timestamp']);
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
                document.getElementById("results").innerHTML = "";
                document.getElementById("results").appendChild(table);
            }
            else{
                var error = JSON.parse((request.response));
                alert("failed to load articles with authorId="+authorId+"\nMessage: "+error['errorMessage']);
            }
        }
    }
}

function doLogin() {
    var email = document.getElementById("loginEmail").value;
    var password = document.getElementById("loginPassword").value;
    var data = {
        "username": email,
        "password": password
    };

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/blogs/api/v1/public/login", true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    xhr.send(JSON.stringify(data));

    xhr.onloadend = function () {
        if(xhr.status === 200)
        {
            alert(xhr.response.toString())
        }
        else
        {
            alert(xhr.status+" " + xhr.response.toString())
        }
    };
}

function validateLoginFields() {
    var userName = document.getElementById("p-username").value.trim();
    var passWord = document.getElementById("p-password").value.trim();
    if(userName.length < 5 || passWord.length < 5)
    {
        alert("please use right credentials to make the call");
        return false;
    }
    return [userName, passWord];
}

function r_getAuthors() {
    var fields = validateLoginFields();
    if(fields !== false)
    {
        var url = "http://localhost:8080/blogs/api/v1/r/authors";
        var request = new XMLHttpRequest();

        request.open("GET", url, true);
        request.setRequestHeader("Content-type", "application/json");
        request.setRequestHeader("Authorization", "Basic " + btoa(fields[0] + ":" + fields[1]));
        request.send();
        request.onload = function () {
            if (request.readyState === request.DONE) {
                var response = request.responseText;
                var obj = JSON.parse(response);
                console.log(response.toString());
            }
            else {
                alert(request.response.toString());
            }
        };
    }
}

function r_bodyContains()
{
    var fields = validateLoginFields();
    if(fields !== false)
    {
        var queryString = document.getElementById("bodyContains").value;

        if(queryString.length < 1 || !queryString.match(/^[A-Za-z0-9]+$/))
        {
            alert("string must be alphabetic characters");
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
    var fields = validateLoginFields();
    if(fields !== false)
    {
        var authorId = document.getElementById("authorIdForInfo").value.trim();
        if(!validateIdField(authorId))
        {
            alert("Invalid input found in the ID field found=" + authorId);
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
                document.getElementById("results").innerHTML = "";
                document.getElementById("results").appendChild(table);

                var rolesTable = getRolesTable(json_data['roles']);
                document.getElementById("results").appendChild(document.createElement("p"));
                document.getElementById("results").appendChild(rolesTable);
                document.getElementById("results").appendChild(document.createElement("p"));
                var articleTable = authorSpecificArticles(json_data["articles"]);
                if(articleTable !== undefined) document.getElementById("results").appendChild(articleTable);
            }
            else alert(request.response.toString());
        }
    }
}

function r_authorArticlesByAuthorId()
{
    var fields = validateLoginFields();
    if(fields !== false)
    {
        var authorId = document.getElementById("authorIdForArticles").value.trim();
        if(!validateIdField(authorId))
        {
            alert("Invalid input found in the ID field found=" + authorId);
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

                var table = authorSpecificArticles(json_data);

                document.getElementById("results").innerHTML = "";
                document.getElementById("results").appendChild(table);
            }
            else alert(request.response.toString());
        }
    }
}

function getRolesTable(jsonRoles)
{
    var table = document.createElement('table');

    for (var i in jsonRoles){
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

function authorSpecificArticles(jsonArticles)
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