const getUrl = (videoIdentifier) => {
    return '/comments/get?video=' + videoIdentifier
};
const postUrl = (videoIdentifier) => {
    return '/comments/add?video=' + videoIdentifier
};
const removeUrl = '/comments/remove?comment=';