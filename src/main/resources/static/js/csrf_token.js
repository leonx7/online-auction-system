var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
console.log(token);
console.log(header);

$(document).ajaxSend(function (e, xhr, options) {
    xhr.setRequestHeader(header, token);
});