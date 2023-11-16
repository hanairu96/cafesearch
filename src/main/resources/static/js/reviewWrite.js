const enrollEnd = ()=>{
    if ($("#input-title").val()==""||$("#input-content").val()==""){
        alert("제목과 내용을 입력하세요.");
    }else {
        let title = $("#input-title").val();
        let cafeId = $("#cafe-id").val();
        let cafeName = $("#cafe-name").text();
        let star = $("#select-star").val();
        let content = $("#input-content").val();

        let query = $("#query").val();
        let index = $("#index").val();

        let postElements = [title, cafeId, cafeName, star, content, query, index];
        let refer = document.referrer; //이전 주소인 카페 상세 페이지

        $.ajax({
            url: "/cafe/review/",
            method: "post",
            data: {postElements: postElements},
            success: data => {
                alert("저장되었습니다.");
                location.replace(refer);
            },
            error: e => {
                alert("저장이 실패하였습니다.");
            }
        });

    }
}

const cancel = ()=>{
    history.back();
}