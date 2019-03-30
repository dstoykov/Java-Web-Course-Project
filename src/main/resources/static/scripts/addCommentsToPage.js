function addCommentsToPage(comments, principal, isModerator) {
    let commentsDiv = $('#commentSection');
    for (comment of comments) {
        commentsDiv.append($('<hr/>'));
        let removeDiv = $('<div class="row">');
        let smallCol = $('<div class="col-sm-1">');
        if (isModerator || principal === comment.author) {
            smallCol.append($(`<button type="button" id="removeBtn" class="close" data-dismiss="alert" name="${comment.id}">&times;</button>`));
        }
        removeDiv.append(smallCol);
        commentsDiv.append(removeDiv);
        let commentDiv = $('<div class="blockquote col-sm-11 wrap">');
        let rightDiv = $('<div align="right">');
        let wrappedP = $('<p class="wrap">');
        wrappedP.append($(`<a>&quot;</a><a>${escapeHtml(comment.content)}</a><a>&quot;</a>`));
        let smallAuthor = $('<small>');
        smallAuthor.append(`<i>${comment.author}&nbsp;</i>`);
        rightDiv.append(wrappedP);
        rightDiv.append(smallAuthor);
        rightDiv.append(`<small>${comment.dateOfPublishing}</small>`);
        commentDiv.append(rightDiv);
        removeDiv.append(commentDiv);
        commentsDiv.append(removeDiv);
    }
}