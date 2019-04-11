const like = () => {
    if (!principal) {
        showNoty('info', 'You must login to like video.');
    } else {
        http.post(likeUrl(videoIdentifier));
        setTimeout(() => {
            http.getLikes(getLikesUrl(videoIdentifier));
            $('#like').hide();
            $('#unlike').show();
        }, 500);
    }
};

const unlike = () => {
    if (principal) {
        http.post(unlikeUrl(videoIdentifier));
        setTimeout(() => {
            http.getLikes(getLikesUrl(videoIdentifier));
            $('#like').show();
            $('#unlike').hide();
        }, 500);
    }
};

function loadLikes(likes) {
    $('#likesCount').text(likes);
}