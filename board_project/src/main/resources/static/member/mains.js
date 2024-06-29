document.addEventListener('DOMContentLoaded', function() {
    var btnJoin = document.querySelector('#btnJoin');
    var pw1 = document.querySelector('#pswd1');
    var pw2 = document.querySelector('#pswd2');
    var pwMsg = document.querySelector('#alertTxt');
    var pwImg1 = document.querySelector('#pswd1_img1');
    var pwImg2 = document.querySelector('#pswd2_img1');
    var pwMsgArea = document.querySelector('.int_pass');
    var error = document.querySelectorAll('.error_next_box');
    var userName = document.querySelector('#name');
    var yy = document.querySelector('#yy');
    var mm = document.querySelector('#mm');
    var dd = document.querySelector('#dd');
    var gender = document.querySelector('#gender');
    var email = document.querySelector('#email');
    var mobile = document.querySelector('#mobile');
    var idInput = document.querySelector('#nickname');

    btnJoin.addEventListener('click', function(event) {
        if (!validateAllInputs()) {
            alert("모든 필드를 올바르게 채워주세요.");
            event.preventDefault(); // 폼 제출 중지
        }
    });

    pw1.addEventListener("input", checkPw);
    pw2.addEventListener("input", comparePw);
    userName.addEventListener("input", checkName);
    yy.addEventListener("input", isBirthCompleted);
    mm.addEventListener("change", isBirthCompleted);
    dd.addEventListener("input", isBirthCompleted);
    gender.addEventListener("change", checkGender);
    email.addEventListener("input", isEmailCorrect);
    mobile.addEventListener("input", checkPhoneNum);
    idInput.addEventListener("input", checkId);

    function validateAllInputs() {
        return checkId() && checkPw() && comparePw() && checkName() && isBirthCompleted() && checkGender() && isEmailCorrect() && checkPhoneNum();
    }

    function checkPw() {
        var pwPattern = /[a-zA-Z0-9~!@#$%^&*()_+|<>?:{}]{8,16}/;
        if (pw1.value === "") {
            error[1].innerHTML = "필수 정보입니다.";
            pwMsg.style.display = "block";
            pwMsgArea.style.paddingRight = "40px";
            pwImg1.src = "m_icon_pass.png";
            error[1].style.display = "block";
            return false;
        } else if (!pwPattern.test(pw1.value)) {
            error[1].innerHTML = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.";
            pwMsg.innerHTML = "사용불가";
            pwMsgArea.style.paddingRight = "93px";
            error[1].style.display = "block";
            pwMsg.style.color = "red";
            pwMsg.style.display = "block";
            pwImg1.src = "m_icon_not_use.png";
            return false;
        } else {
            error[1].style.display = "none";
            pwMsg.innerHTML = "안전";
            pwMsgArea.style.paddingRight = "93px";
            pwMsg.style.color = "#03c75a";
            pwMsg.style.display = "block";
            pwImg1.src = "m_icon_safe.png";
            return true;
        }
    }

    function comparePw() {
        if (pw2.value === "") {
            error[2].innerHTML = "필수 정보입니다.";
            error[2].style.display = "block";
            return false;
        } else if (pw2.value !== pw1.value) {
            pwImg2.src = "m_icon_check_disable.png";
            error[2].innerHTML = "비밀번호가 일치하지 않습니다.";
            error[2].style.display = "block";
            return false;
        } else {
            pwImg2.src = "m_icon_check_enable.png";
            error[2].style.display = "none";
            return true;
        }
    }

    function checkName() {
        var namePattern = /[a-zA-Z가-힣]/;
        if (userName.value === "") {
            error[3].innerHTML = "필수 정보입니다.";
            error[3].style.display = "block";
            return false;
        } else if (!namePattern.test(userName.value) || userName.value.indexOf(" ") > -1) {
            error[3].innerHTML = "한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)";
            error[3].style.display = "block";
            return false;
        } else {
            error[3].style.display = "none";
            return true;
        }
    }

    function isBirthCompleted() {
        var year = parseInt(yy.value, 10);
        var month = parseInt(mm.value, 10);
        var day = parseInt(dd.value, 10);

        if (!year || isNaN(year) || year < 1900 || year > new Date().getFullYear()) {
            error[4].innerHTML = "유효한 태어난 년도 4자리를 입력하세요.";
            error[4].style.display = "block";
            return false;
        } else if (!month || isNaN(month) || month < 1 || month > 12) {
            error[4].innerHTML = "태어난 월을 선택하세요.";
            error[4].style.display = "block";
            return false;
        } else if (!day || isNaN(day) || day < 1 || day > 31) {
            error[4].innerHTML = "태어난 일(날짜)을 정확하게 입력하세요.";
            error[4].style.display = "block";
            return false;
        } else if (!isBirthRight(year, month, day)) {
            error[4].style.display = "block";
            return false;
        } else {
            error[4].style.display = "none";
            return true;
        }
    }

    function isBirthRight(year, month, day) {
        var date = new Date(year, month - 1, day);
        if (date.getFullYear() !== year || date.getMonth() + 1 !== month || date.getDate() !== day) {
            error[4].innerHTML = "생년월일을 다시 확인해주세요.";
            error[4].style.display = "block";
            return false;
        } else {
            return true;
        }
    }

    function checkGender() {
        var valid = gender.value !== "성별";
        if (!valid) {
            error[5].innerHTML = "필수 정보입니다.";
            error[5].style.display = "block";
        } else {
            error[5].style.display = "none";
        }
        return valid;
    }

    function isEmailCorrect() {
        var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        if (email.value === "") {
            error[0].innerHTML = "이메일 주소를 입력해주세요.";
            error[0].style.display = "block";
            return false;
        } else if (!emailPattern.test(email.value)) {
            error[0].innerHTML = "유효한 이메일 주소를 입력해주세요.";
            error[0].style.display = "block";
            return false;
        } else {
            error[0].style.display = "none";
            return true;
        }
    }

    function checkPhoneNum() {
        var phonePattern = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
        var errorIndex = 6; // 에러 메시지를 표시할 인덱스
        if (mobile.value === "") {
            error[errorIndex].innerHTML = "필수 정보입니다.";
            error[errorIndex].style.display = "block";
            return false;
        } else if (!phonePattern.test(mobile.value)) {
            error[errorIndex].innerHTML = "형식에 맞지 않는 번호입니다.";
            error[errorIndex].style.display = "block";
            return false;
        } else {
            error[errorIndex].style.display = "none";
            return true;
        }
    }

    function checkId() {
        var idPattern = /^[a-zA-Z0-9가-힣-_]{2,8}$/;
        var errorIndex = 7; // 닉네임의 오류 메시지 위치를 나타내는 인덱스
        var errorMessage = error[errorIndex]; // 해당 인덱스의 오류 메시지 요소를 선택
        if (idInput.value === "") {
            errorMessage.innerHTML = "필수 정보입니다.";
            errorMessage.style.display = "block";
            return false;
        } else if (!idPattern.test(idInput.value)) {
            errorMessage.innerHTML = "2~8자의 영문 소문자, 숫자만 사용 가능합니다.";
            errorMessage.style.display = "block";
            return false;
        } else {
            errorMessage.innerHTML = "멋진 아이디네요!";
            errorMessage.style.color = "#08A600";
            errorMessage.style.display = "block";
            return true;
        }
    }

    var form = document.querySelector("form");
    form.addEventListener("submit", function(event) {
        event.preventDefault(); // 기본 폼 제출 방지

        if (!checkId()) {
            alert("닉네임을 입력해주세요.");
            return;
        }

        var memberEmail = email.value;
        $.ajax({
            url: '/check-email', // 이메일 중복 검사를 처리하는 URL
            type: 'POST',
            data: { memberEmail: memberEmail },
            dataType: 'json'
        })
            .done(function(data) {
                console.log(data);  // 서버 응답 로깅
                if (data.exists) {
                    alert("이미 존재하는 이메일입니다."); // 중복 알림
                } else {
                    // 중복이 없을 경우 폼을 서버에 제출하고, 성공적으로 제출된 후 페이지를 새로고침
                    form.submit();
                    window.setTimeout(function() {
                        location.reload(true); // 폼 제출 후 페이지를 새로고침
                    }, 1000); // 폼 제출 후 충분한 시간을 주어 서버가 처리할 수 있도록 합니다
                }
            })
            .fail(function(xhr, status, error) {
                alert("오류가 발생했습니다. 다시 시도해주세요."); // 오류 알림
            });
    });
});