const http = (() => {
    const get = (url, principal, isModerator) => fetch(url, {method: 'GET'})
        .then(response => response.json())
        .then(data => {
            if (principal) {
                commentsToPage(data, principal, isModerator)
            } else {
                loadLikes(data);
            }
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
    return {get, post};
})();