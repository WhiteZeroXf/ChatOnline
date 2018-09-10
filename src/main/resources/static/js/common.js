function ready(callback) {
    window.onload = callback;
}

/**
 * 异步请求 Fetch json 格式
 * @param url
 * @param data
 * @param callback
 */
function jsonFetch(url, data, callback) {
    fetch('http://' + serverHost + url, {
        method: "POST",
        data: data || {'default': 'null'},
    })
        .then(res => res.json())
        .catch(function () {
            alert('请求失败');
        })
        .then(json => callback(json))
        .catch(function () {
            alert('数据返回出错');
        });
}

/**
 * 异步请求 Fetch FormData 格式
 * @param url
 * @param data
 * @param callback
 */
function formDataFetch(url, data, callback) {
    fetch('http://' + serverHost + url, {
        method: "POST",
        body: data || null,
    })
        .then(res => res.json())
        .catch(function () {
            alert('请求失败');
        })
        .then(json => callback(json))
        .catch(function () {
            alert('数据返回出错');
        });
}

/**
 *  WebSocket 通信
 * @param url
 * @param message
 * @constructor
 */
function SimpleWebSocket(url, message) {

    let sum = 0;
    if ("WebSocket" in window) {

        let ws = new WebSocket("ws://" + serverHost + url);

        ws.onopen = function () {
            ws.send(JSON.stringify(Message(0, '你好')));
        };

        ws.onclose = function () {
            alert("连接已关闭...");
        };

        ws.onmessage = function (evt) {
            if (sum++ === 0)
                return;
            alert(evt.data);
        };

        SimpleWebSocket.prototype.send = function (message) {
            ws.send(JSON.stringify(message));
        };

    }
    else {
        // 浏览器不支持 WebSocket
        alert("您的浏览器不支持 WebSocket!");
    }
}

function Session() {
}

Session.prototype.get = function (key) {
    return sessionStorage.getItem(key);
};
Session.prototype.set = function (key, value) {
    return sessionStorage.setItem(key, value);
};
Session.prototype.clear = function () {
    return sessionStorage.clear();
};
Session.prototype.key = function (index) {
    return sessionStorage.key(index);
};
Session.prototype.lenght = function () {
    return sessionStorage.length();
};
Session.prototype.remove = function (key) {
    return sessionStorage.removeItem(key);
};

const session = new Session();

/**
 * 通过 选择器 或传入的 DOM 对象 将其设置为 显示
 * @param object
 */
function showArea(object) {
    if (typeof object === "string")
        document.querySelector(object).style.display = 'block';
    let flag = isDom(object);
    if (flag)
        object.style.display = 'block';
}

/**
 * 通过 选择器 或传入的 DOM 对象 将其设置为 隐藏
 * @param object
 */
function hideArea(object) {
    if (typeof object === "string")
        document.querySelector(object).style.display = 'none';
    let flag = isDom(object);
    if (flag)
        object.style.display = 'none';

}

/**
 * 判断是否为 DOM 对象
 */
isDom =
    (typeof HTMLElement === 'object') ?
        function (obj) {
            return obj instanceof HTMLElement;
        } :
        function (obj) {
            return obj && typeof obj === 'object' && obj.nodeType === 1 && typeof obj.nodeName === 'string';
        };

function $(selector) {
    return document.querySelector(selector);
}

/**
 * @return {string}
 */
function Message(type, text, to, from) {
    let message = {};
    message.type = type || 0;
    message.to = to || '';
    message.text = text || 'first';
    message.token = token || '';
    message.from = from || '';
    return message;
}





