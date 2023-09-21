let mainName = document.getElementById("banner-name");
mainName.addEventListener("click", e =>{
    location.assign("/cafe/");
});

document.querySelector("#search-box>img").addEventListener("click", e =>{
    document.querySelector(".search-form").submit();
});

function login(){
    location.assign("/cafe/member/loginPage/");
}

function logout(){
    let check = confirm("로그아웃 하시겠습니까?");
    if (check){
        location.replace("/logout");
    }
}

function myPage(){

}