const updateEnd = ()=>{
    if ($("#input-title").val()==""||$("#input-content").val()==""){
        alert("제목과 내용을 입력하세요.");
    }else {
        let title = $("#input-title").val();
        let cafeName = $("#cafe-name").text();
        let star = $("#select-star").val();
        let content = $("#input-content").val();

        let query = $("#query").val();
        let index = $("#index").val();

        let putElements = [title, cafeName, star, content, query, index];
        let refer = document.referrer; //이전 주소인 카페 상세 페이지

        let elements = JSON.stringify(putElements);

        $.ajax({
            url: "/cafe/review/"+reviewNo,
            type: "put",
            data: elements,
            contentType: "application/json;charset=UTF-8",
            success: data => {
                alert("수정되었습니다.");
                location.replace(refer);
            },
            error: e => {
                alert("수정에 실패하였습니다.");
            }
        });

    }
}

const cancel = ()=>{
    history.back();
}