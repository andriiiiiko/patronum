function storeFilterState() {
    var showActive = document.querySelector('input[name="showActive"]').checked;
    sessionStorage.setItem('showActive', showActive);
}

function submitForm() {
    storeFilterState();

    var showActive = sessionStorage.getItem('showActive') === 'true';

    if (showActive) {
        window.location.href = '/mvc/home/active';
    } else {
        window.location.href = '/mvc/home';
    }
}

document.addEventListener('DOMContentLoaded', function () {
    var showMy = sessionStorage.getItem('showMy') === 'true';
    var showActive = sessionStorage.getItem('showActive') === 'true';

    document.querySelector('input[name="showMy"]').checked = showMy;
    document.querySelector('input[name="showActive"]').checked = showActive;
});

function redirectToUrl(element) {
    var urlId = $(element).attr("data-url-id");

    var form = $('<form>', {
        'action': '/mvc/home/redirect/' + urlId,
        'method': 'post'
    });

    form.appendTo('body').submit().remove();
}


