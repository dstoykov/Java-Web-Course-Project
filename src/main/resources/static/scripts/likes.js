const like = () => {
    http.post(likeUrl(videoIdentifier));
    setTimeout(() => {
        http.get(getLikesUrl(videoIdentifier));
        $('#like').hide();
        $('#unlike').show();
    }, 500);
};

const unlike = () => {
    http.post(unlikeUrl(videoIdentifier));
    setTimeout(() => {
        http.get(getLikesUrl(videoIdentifier));
        $('#like').show();
        $('#unlike').hide();
    }, 500);
};

function loadLikes(likes) {
    $('#likesCount').text(likes);
}