var stompClient = null;

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/bidResponse', function (bidResponse) {
            showBidResponse(JSON.parse(bidResponse.body).content);
            showUsername(JSON.parse(bidResponse.body).username);
            showCurrentBid(JSON.parse(bidResponse.body).currentBid);
        });
    });
}

function sendBidRequest() {
    stompClient.send("/app/bidRequest", {}, JSON.stringify({
        'id': $("#id").val(),
        'bid': $("#bid").val(),
        'increment': $("#increment").val(),
    }));
    $("#bidToken").text("bid");
}

function sendAutoBidRequest() {
    stompClient.send("/app/autoBidRequest", {}, JSON.stringify({
        'id': $("#id").val(),
        'bid': $("#bid").val(),
        'increment': $("#increment").val(),
    }));
}

function showBidResponse(bidResponse) {
    $("#bid").val(bidResponse);
}

function showUsername(bidResponse) {
    let username = $('#username').text();
    console.log(username);
    console.log(bidResponse);
    if (bidResponse == username) {
        $("#bidToken").text("bid");
        $("#bidLabel").css("color", "green");
        $("#bid").css("color", "green");
        $("#bidLabel").text("Your bid wins!");
    } else if ((bidResponse != username) && ($("#bidToken").text() == "bid")) {
        $("#bidLabel").css("color", "red");
        $("#bid").css("color", "red");
        $("#bidLabel").text("Your bid has been exceeded!");
    }
}

function showCurrentBid(bidResponse) {
    $("#currentBid").text("Current bid " + bidResponse + " BYR");
}

$(function () {
    $("#bidBtn").on('submit', function (e) {
        e.preventDefault();
    });
    $("#bidBtn").click(function () {
        sendBidRequest();
        sendAutoBidRequest()
    });
});

$(function () {
    $("#autoBidBtn").click(function (event) {
        event.preventDefault();

        let id = $("#id").val();
        let maximumBid = $("#autoBid").val();
        let autoBidDto = new Object();
        autoBidDto.id = id;
        autoBidDto.maximumBid = maximumBid;

        $.ajax({
            url: '/autoBid',
            data: autoBidDto,
            type: "POST",
            success: function () {
                $("#autoBidLabel").css("color", "green");
                $("#autoBidLabel").text("Auto bid was successfully placed!")
            },
            error: function (a) {
                console.log(a);
            }
        });
    });
});