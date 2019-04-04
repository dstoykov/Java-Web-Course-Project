const getCommentsUrl = (videoIdentifier) => {
    return '/comments/get?video=' + videoIdentifier
};
const addCommentUrl = (videoIdentifier) => {
    return '/comments/add?video=' + videoIdentifier
};
const getLikesUrl = (videoIdentifier) => {
    return '/videos/' + videoIdentifier + '/likes';
};
const likeUrl = (videoIdentifier) => {
    return '/videos/' + videoIdentifier + '/like';
};
const unlikeUrl = (videoIdentifier) => {
    return '/videos/' + videoIdentifier + '/unlike';
};
const removeCommentUrl = '/comments/remove?comment=';