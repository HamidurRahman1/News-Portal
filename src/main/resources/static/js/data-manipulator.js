function change()
{
    var request = new XMLHttpRequest();

    request.open('GET', 'http://localhost:8080/blogs/api/v1/public/articles', true);
    request.onload = function () {

        var data = JSON.parse(this.response);

        if (request.status >= 200 && request.status < 400) {
            var i = 0;
            for(i; i< data.length; i++)
            {
                document.getElementById("d").innerHTML += data[i]['title'];
                document.getElementById("d").innerHTML += "\t";
                document.getElementById("d").innerHTML += data[i]['body'];
                document.getElementById("d").innerHTML += "<br>";
            }
        } else {
            document.getElementById("d").innerHTML = "error getting data";
        }
    };

    request.send()
}