
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

function loadCommentsByArticleId() {
    var r = new XMLHttpRequest();
    var articleId = document.getElementById("articleId").value;
    console.log(articleId);
    r.open("GET", "http://localhost:8080/blogs/api/v1/public/article/"+ articleId + "/comments");
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
            var error = JSON.parse((r.response));
            alert("failed to load comments with articleId="+articleId+"\nMessage: "+error['errorMessage']);
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

function checkLogin() {
    var userName = document.getElementById("p-username").value;
    var passWord = document.getElementById("p-password").value;
    if(userName.length < 5 || passWord.length < 5)
    {
        alert("please use right credentials to make the call");
        return false;
    }
    return [userName, passWord];
}

function r_getAuthors() {
    var fields = checkLogin();
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
    var fields = checkLogin();
    if(fields !== false)
    {
        var queryString = document.getElementById("bodyContains").value;

        if(queryString.length < 1 || !queryString.match(/^[A-Za-z ]+$/))
        {
            alert("string must be alphabetic characters");
            return;
        }
        else
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
                    alert(response.toString());
                }
                else {
                    console.log(request.response.toString());
                    alert(request.response.toString());
                }
            }
        }
    }
}