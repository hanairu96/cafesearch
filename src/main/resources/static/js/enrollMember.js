//아이디 사용 가능 여부 확인
$("#inputId").keyup(e=>{
    let inputId = $("#inputId").val().trim();
    $.ajax({
        url:"/cafe/member/idDuplicateCheck",
        data: {inputId: inputId},
        success:data=>{
            console.log(data);
            if(!$.isEmptyObject(data)){ //중복된 아이디
                $("span#checkId>small").text("이미 존재하는 아이디입니다.").css("color","red");
            }else if($("#inputId").val()==""){
                $("span#checkId>small").text("아이디를 입력해주세요.").css("color","red");
            }else if($("#inputId").val().length<5){
                $("span#checkId>small").text("아이디는 5자리 이상 입력해주세요.").css("color","red");
            }else if(!$("#inputId").val().trim().match(/^[A-Za-z0-9]{5,12}$/)){
                $("span#checkId>small").text("아이디는 영문자와 숫자만 입력해주세요.").css("color","red");
            }else{
                $("span#checkId>small").text("사용 가능한 아이디입니다.").css("color","green");
            }
        }
    })
});

//비밀번호 재확인
$(()=>{
    $("#pwdCheck").keyup(e=>{
        const pw=$("#inputPwd").val();
        const pwck=$(e.target).val();
        if(pw==pwck){//비밀번호가 일치할 때
            if(pwck!=""){//비밀번호 재확인 칸이 비어있지 않으면
                $("span#checkPwd>small").css("color","green").text("비밀번호가 일치합니다.");
            }else{
                $("span#checkPwd>small").text(" ");
            }
        }else{//비밀번호가 불일치할 때
            if(pwck==""){//비밀번호 재확인 칸이 비어있으면
                $("span#checkPwd>small").text(" ");
            }else{
                $("span#checkPwd>small").css("color","red").text("비밀번호가 불일치합니다.");
            }
        }
    })
});

const fn_enrollFail = () => {
    //아이디 사용 가능 여부
    if (!($("span#checkId>small").text().includes("가능한"))) { //아이디 사용 가능하다는 말이 없으면
        alert("아이디를 확인해주세요.");
        $("#inputId").focus();
        return false;
    }
    //비밀번호 필수입력
    const inputPwd = $("#inputPwd").val().trim();
    //영문, 숫자, 특수문자 1개 이상씩 사용하여 8~15자리 입력조건
    const pwdReg  = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
    if (inputPwd.match(pwdReg) == null) { //비밀번호가 양식대로 입력되지 않았으면
        alert("비밀번호는 영문자와 숫자, 특수문자를 각각 하나 이상 포함하여 8~15자리로 입력하세요.");
        $("#inputPwd").focus();
        return false;
    }
    if ($("span#checkPwd>small").text().includes("불")) { //비밀번호가 불일치하면
        alert("비밀번호 재확인에 비밀번호를 정확히 적어주세요.");
        $("#pwdCheck").focus(); //다시 입력
        return false;
    }
    if ($("#pwdCheck").val().trim() == "") {
        alert("비밀번호 재확인에 비밀번호를 적어주세요.");
        $("#pwdCheck").focus();
        return false;
    }
    //이름 정규식 표현
    const inputName = $("#inputName").val().trim();
    const nameReg = /^[가-힣a-zA-Z][가-힣a-zA-Z]+$/;
    if (!nameReg.test(inputName)) { //이름이 잘못됐으면
        alert("이름을 확인해주세요.");
        $("span#checkName>small").text("올바른 이름을 입력해주세요.").css("color", "red");
        $("#inputName").focus();
        return false;
    } else {
        $("span#checkName>small").text(" ");
    }
    //닉네임 정규식 표현
    const inputNickname = $("#inputNickname").val().trim();
    const nicknameReg = /^[가-힣a-zA-Z][가-힣a-zA-Z0-9]+$/;
    if (!nicknameReg.test(inputNickname)) { //닉네임이 잘못됐으면
        alert("닉네임을 확인해주세요.");
        $("span#checkNickname>small").text("올바른 닉네임을 입력해주세요.").css("color", "red");
        $("#inputNickname").focus();
        return false;
    } else {
        $("span#checkNickname>small").text(" ");
    }
    //년도 입력
    const yy = $("#yy").val().trim();
    const pattern = /^(19|20)\d{2}$/; //1900~2099년만
    if (yy == "" || pattern.test(yy)) {
        $("span#checkYear>small").text(" ");
    } else { //연도 입력이 잘못 되었으면
        alert("연도를 확인해주세요.");
        $("span#checkYear>small").text("올바른 연도를 입력해주세요.").css("color", "red");
        $("#yy").focus();
        return false;    }
    //핸드폰 번호 입력
    const inputPhone = $("#inputPhone").val().trim();
    const phoneReg = /^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$/; //핸드폰 번호(- 없음)
    if (inputPhone == "" || phoneReg.test(inputPhone)) {
        $("span#checkPhone>small").text(" ");
    } else {
        alert("핸드폰 번호를 확인해주세요.");
        $("span#checkPhone>small").text("올바른 번호를 입력해주세요.").css("color", "red");
        $("#inputPhone").focus();
        return false;
    }
    //이메일 입력
    const inputEmail = $("#inputEmail").val().trim();
    let emailReg = /^[0-9a-zA-Z_]+@[a-zA-Z]+.[a-zA-Z]{2,3}$/i;
    if (inputEmail != "") {
        if (!emailReg.test(inputEmail)) {
            alert("이메일을 확인해주세요.");
            $("span#checkEmail>small").text("올바른 이메일을 입력해주세요.").css("color", "red");
            $("#inputEmail").focus();
            return false;
        }
    }
    //가입 여부 확인
    let apply = confirm("회원가입하시겠습니까? 가입하신 정보는 수정 가능합니다.")
    if (apply) {
        alert("회원가입이 완료 되었습니다.");
        return true;
    }else {
        return false;
    }
}