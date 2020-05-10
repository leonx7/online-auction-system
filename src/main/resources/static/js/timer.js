function initializeTimer(date, id) {
    // Set the date we're counting down to
    var countDownDate = new Date(date).getTime();

    var callback = function () {

        // Get today's date and time
        var now = new Date().getTime();

        // Find the distance between now and the count down date
        var distance = countDownDate - now;

        // Time calculations for days, hours, minutes and seconds
        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        hours = hours < 10 ? "0" + hours : hours;
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        // Display the result in the element with id="demo"
        if (days > 1) {
            document.getElementById('timerLabel').innerHTML = 'Lot close';
            document.getElementById(id).innerHTML = date;
        } else if (distance < 0) {
            clearInterval(x);
            document.getElementById(id).innerHTML = "LOT CLOSED";
            $(document).ready(function () {
                $("#container :input").attr("disabled", true);
            });
        } else {
            $('#timer').css("color", "red");
            document.getElementById(id).innerHTML = hours + " : "
                + minutes + " : " + seconds;
        }
    }

    callback();
    // Update the count down every 1 second
    var x = setInterval(callback, 1000);
}