$('#create-btn').click(function() {
    var huntName=$('#huntName').val();
    var startingCity=$('#startingCity').val();
    var numPaces=$('#numPaces').val();
    var url="api/vault/" + huntName + "/" + startingCity +"/" + numPaces;
    console.log("Calling: " + url);
    $('#response-body-vault').html('Calling REST endpoint');
    if ( huntName === "" || startingCity === "" || numPaces === "") {
        $('#response-body-vault').html('Please enter a "Hunt Name", "Starting City", and a "Number of Paces".');
    } else {
        $.ajax({
            type: 'PUT',
            url: url,
            success: function(){
                $('#response-body-vault').html("ok");
            },
            error: function(xhr, status, error) {
                $('#response-body-vault').html(
                    "status: " + status + "<br>" +
                    "error: " + error + "<br>" +
                    "xhr: " + "<pre>" + syntaxHighlight(xhr) + "</pre>"
                );
            },
            timeout: 7000
        });
    }
});
$('#get-btn').click(function() {
    var huntName=$('#huntName').val();
    var url="api/vault/" + huntName;
    console.log("Calling: " + url);
    $('#response-body-vault').html('Calling REST endpoint');
    if ( huntName === "" ) {
        $('#response-body-vault').html('Please enter a "Hunt Name".');
    } else {
        $.ajax({
            type: 'GET',
            url: url,
            success: function(result){
                var ret = "<table><tr><th>Vault Response</th></tr>";
                ret += "<tr><td>"+syntaxHighlight(result)+"</td></tr>";
                ret += "</table>";
                $('#response-body-vault').html(ret);
            },
            error: function(xhr, status, error) {
                $('#response-body-vault').html(
                    "status: " + status + "<br>" +
                    "error: " + error + "<br>" +
                    "xhr: " + "<pre>" + syntaxHighlight(xhr) + "</pre>"
                );
            },
            timeout: 7000,
            dataType: 'json'
        });
    }
});
$('#list-btn').click(function() {
    var url="api/vault/list";
    console.log("Calling: " + url);
    $('#response-body-vault').html('Calling REST endpoint');
    $.ajax({
        type: 'GET',
        url: url,
        success: function(result){
            var ret = "<table><tr><th>Hunt Names</th></tr>";
            for (var i in result) {
                ret += "<tr><td>"+result[i]+"</td></tr>";
            }
            ret += "</table>";
            $('#response-body-vault').html(ret);
        },
        error: function(xhr, status, error) {
            $('#response-body-vault').html(
                "status: " + status + "<br>" +
                "error: " + error + "<br>" +
                "xhr: " + "<pre>" + syntaxHighlight(xhr) + "</pre>"
            );
        },
        timeout: 7000,
        dataType: 'json'
    });
});
$('#delete-btn').click(function() {
    var huntName=$('#huntName').val();
    var url="api/vault/" + huntName;
    console.log("Calling: " + url);
    $('#response-body-vault').html('Calling REST endpoint');
    if ( huntName === "" ) {
        $('#response-body-vault').html('Please enter a "Hunt Name".');
    } else {
        $.ajax({
            type: 'DELETE',
            url: url,
            success: function(){
                $('#response-body-vault').html("ok");
            },
            error: function(xhr, status, error) {
                $('#response-body-vault').html(
                    "status: " + status + "<br>" +
                    "error: " + error + "<br>" +
                    "xhr: " + "<pre>" + syntaxHighlight(xhr) + "</pre>"
                );
            },
            timeout: 7000
        });
    }
});
$('#vcap-btn').click(function() {
    var url="api/vault/vcap";
    console.log("Calling: " + url);
    $('#response-body-vault').html('Calling REST endpoint');
    $.ajax({
        type: 'GET',
        url: url,
        success: function(result){
            var ret = "<table><tr><th>VCAP Key</th><th>VCAP Value</th></tr>";
            for (var i in result) {
                ret += "<tr><td>"+i+"</td><td>"+result[i]+"</td></tr>";
            }
            ret += "</table>";
            $('#response-body-vault').html(ret);
        },
        error: function(xhr, status, error) {
            $('#response-body-vault').html(
                "status: " + status + "<br>" +
                "error: " + error + "<br>" +
                "xhr: " + "<pre>" + syntaxHighlight(xhr) + "</pre>"
            );
        },
        timeout: 7000,
        dataType: 'json'
    });
});
//https://stackoverflow.com/a/7220510
function syntaxHighlight(json) {
    if (typeof json !== 'string') {
         json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        } //else if ('"'.test(match)) {
          //  cls = syntaxHighlight(match)
        //}
        return '<span class="' + cls + '">' + match + '</span>';
    });
}
var localhost = window.location.hostname;
var restResponse = 'curl -X PUT "https://' + localhost + '/api/vault/{huntName}/{startingCity}/{numberOfPaces}'+'<br>';
restResponse += 'curl -X GET "https://' + localhost + '/api/vault/{huntName}"<br>';
restResponse += 'curl -X DELETE "https://' + localhost + '/api/vault/{huntName}"<br>';
restResponse += 'curl -X GET "https://' + localhost + '/api/vault/list"<br>';
restResponse += 'curl -X GET "https://' + localhost + '/api/vault/vcap"<br>';
$('#response-body-rest').html(restResponse);