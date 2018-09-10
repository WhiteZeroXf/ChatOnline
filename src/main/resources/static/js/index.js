let ws;
let token;
ready(function () {

    function jumpPhotos(data) {
        for (let i in data)
            window.open("http://" + serverHost + data[i]);
    }

    $('#onsub').onclick = function () {
        let form = new FormData(document.getElementById('fileinfo'));
        console.log(form.length);
        formDataFetch("/test", form, function (data) {
            jumpPhotos(data);
        });
    };

    function updateUserList(json) {
        let html = '';
        for (let i in json.data.userList) {
            html += "<option value='" + json.data.userList[i] + "'>" + json.data.userList[i] + "</option>";
        }
        $('#userList').innerHTML = html;
        console.log(json);
    }

    function showMessage(json) {
        //TODO
        alert('来自 ' + json.from + ' 的消息：\n' + json.text);
    }

    ws = new WebSocket("ws://" + serverHost + "/server");

    ws.onopen = function () {
        ws.send(JSON.stringify(Message(0, '你好')));
    };
    ws.onclose = function () {
        ws.close();
    };
    ws.onmessage = function (evt) {
        let json = JSON.parse(evt.data);
        switch (json.type) {
            case 100:
                token = json.token;
                break;
            case 1:
                updateUserList(json);
                break;
            case 2:
                showMessage(json);
                break;
            case 200:
                alert('发送成功');
                break;
            case 404:
                alert('发送用户未找到');
                break;
            default:
                console.log('type:' + json.type + '\ntext:' + json.text + '\nto:' + json.to + "\ntoken:" + json.token);

        }
    };

    $('#send').onclick = function () {
        let text = $('#message').value;
        let to = $('#userList').value;
        ws.send(JSON.stringify(Message(2, text, to, token)));
    };

    showArea('#index');

});
window.onbeforeunload = function () {
    let n = window.event.screenX - window.screenLeft;
    let b = n > document.documentElement.scrollWidth - 20;
    if (b && window.event.clientY < 0 || window.event.altKey) {
        //alert("这是一个关闭操作而非刷新");
        ws.close();
        window.event.returnValue = function () {
            ws.close();
        } //此处放你想要操作的代码
    } else {
        //alert("这是一个刷新操作而非关闭");
        ws.close();
    }
};