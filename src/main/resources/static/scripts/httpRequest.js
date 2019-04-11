const http = (() => {
    const getComments = (url, principal, isModerator) => fetch(url, {method: 'GET'})
        .then(response => response.json())
        .then(data => {
            commentsToPage(data, principal, isModerator)
        })
        .catch(error => console.log(error));

    const getLikes = (url) => fetch(url, {method: 'GET'})
        .then(response => response.json())
        .then(data => {
            loadLikes(data);
        })
        .catch(error => console.log(error));

    const post = (url, data) => fetch(url, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    return {getComments, getLikes, post};
})();