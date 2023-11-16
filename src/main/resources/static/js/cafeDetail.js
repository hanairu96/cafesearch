let url = new URL(window.location.href); //현재 페이지 URL
let urlParams = url.searchParams; //URL의 파라미터들
let query = urlParams.get("query");
let index = urlParams.get("index");

const reviewPost = (cafeId, cafeName, loginMember)=>{
    let checkElements = [cafeId, loginMember];
    $.ajax({
        url: "/cafe/review/idDuplicateCheck",
        data: {checkElements: checkElements},
        success: data => {
            if(!$.isEmptyObject(data)) { //존재하는 아이디면
                alert("이미 등록한 리뷰가 존재합니다.");
            }else {
                location.assign("/cafe/reviewWrite?index="+index+"&query="+query+"&id="+cafeId+"&name="+cafeName);
            }
        }
    });
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

// 네이버 지도 API
var mapOptions = {
    center: new naver.maps.LatLng(mapy, mapx),
    zoom: 16
}

var map = new naver.maps.Map('map', mapOptions);

var marker = new naver.maps.Marker({
    position: new naver.maps.LatLng(mapy, mapx),
    map: map
});
