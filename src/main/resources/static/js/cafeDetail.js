let url = new URL(window.location.href); //현재 페이지 URL
let urlParams = url.searchParams; //URL의 파라미터들
let query = urlParams.get("query");
let index = urlParams.get("index");

const reviewPost = (cafeName)=>{
    location.assign("/cafe/reviewWrite?index="+index+"&query="+query+"&name="+cafeName);
}

const reviewUpdate = (reviewNo)=>{
    location.assign("/cafe/reviewUpdate?index="+index+"&query="+query+"&reviewNo="+reviewNo);
}

const reviewDelete = (reviewNo)=>{
    let check = confirm("정말로 삭제하시겠습니까?");
    if (check){
        $.ajax({
            url: "/cafe/review/"+reviewNo,
            type: "DELETE",
            success: data => {
                alert("삭제되었습니다.");
                location.reload();
            },
            error: e => {
                alert("삭제에 실패하였습니다.");
            }
        });
    }
}