const commentWrite = () => {
    const writer = document.getElementById("commentWriter").value.trim();
    const contents = document.getElementById("commentContents").value.trim();
    const postId = document.getElementById('post').getAttribute('data-post-id');

    console.log("게시글 번호:", postId);
    console.log("작성자:", writer);
    console.log("내용:", contents);

    if (!writer || !contents) {
        console.error("작성자와 내용은 필수입니다.");
        alert("작성자와 내용은 필수입니다.");
        return;
    }

    $.ajax({
        type: "post",
        url: "/comment/save",
        data: {
            "commentWriter": writer,
            "commentContents": contents,
            "boardId": parseInt(postId)
        },
        success: function(res) {
            console.log("요청 성공", res);
        },
        error: function(err) {
            console.log("요청 실패", err);
            alert("댓글 저장 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
    });
}