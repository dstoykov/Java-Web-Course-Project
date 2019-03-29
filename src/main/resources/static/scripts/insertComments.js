function addCommentsToPage(comments) {
    let commentsDiv = $('#commentSection');
    for (aComment of comments) {
        commentsDiv.append($('<hr/>'));
        let removeDiv = $('<div class="row">');
        let smallCol = $('<div class="col-sm-1">');
        smallCol.append($('<button type="button" class="close" data-dismiss="alert">&times;</button>'));
        removeDiv.append(smallCol);
        commentsDiv.append(removeDiv);
        let commentDiv = $('<div class="blockquote col-sm-11 wrap">');
        let rightDiv = $('<div align="right">');
        let wrappedP = $('<p class="wrap">');
        wrappedP.append($(`<a>&quot;</a><a>${escapeHtml(aComment.content)}</a><a>&quot;</a>`));
        let smallAuthor = $('<small>');
        smallAuthor.append(`<i>${aComment.author}&nbsp;</i>`);
        rightDiv.append(wrappedP);
        rightDiv.append(smallAuthor);
        rightDiv.append(`<small>${aComment.dateOfPublishing}</small>`);
        commentDiv.append(rightDiv);
        removeDiv.append(commentDiv);
        commentsDiv.append(removeDiv);
    }
}