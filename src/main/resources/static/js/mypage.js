document.addEventListener('DOMContentLoaded', () => {
    // 원본 데이터
    let originalZip = document.querySelector('.zip').dataset.userZip;
    let originalAddr1 = document.querySelector('.addr1').dataset.userAddr1;
    let originalAddr2 = document.querySelector('.addr2').dataset.userAddr2;
    let originalPhone = document.querySelector('.phone').dataset.userPhone;

    // 요소들
    const zip = document.getElementById('zip');
    const addr1 = document.getElementById('addr1');
    const addr2 = document.getElementById('addr2');
    const phone = document.getElementById('phone');

    // 주소 변경
    function changeAddress() {
        zip.value = originalZip;
        addr1.value = originalAddr1;
        addr2.value = '';
        addr2.removeAttribute('readonly');
        addr2.setAttribute('placeholder', '상세주소');

        zip.style.borderWidth = '1px';
        addr1.style.borderWidth = '1px';
        addr2.style.borderWidth = '1px';

        document.getElementById('saveAddress').style.display = 'inline-block';
        document.getElementById('cancelAddress').style.display = 'inline-block';
        document.getElementById('findAddressBtn').style.display = 'inline-block';
        document.getElementById('btnAddress').style.display = 'none';
    }

    // 주소 변경 취소
    function cancelAddress() {
        zip.value = originalZip;
        addr1.value = originalAddr1;
        addr2.value = originalAddr2;
        zip.setAttribute('readonly', true);
        addr1.setAttribute('readonly', true);
        addr2.setAttribute('readonly', true);
        addr2.setAttribute('placeholder', '');

        zip.style.borderWidth = '0px';
        addr1.style.borderWidth = '0px';
        addr2.style.borderWidth = '0px';

        document.getElementById('saveAddress').style.display = 'none';
        document.getElementById('cancelAddress').style.display = 'none';
        document.getElementById('findAddressBtn').style.display = 'none';
        document.getElementById('btnAddress').style.display = 'inline-block';
    }

    // 전화번호 포맷팅
    function formatPhoneNumber() {
        let rawValue = phone.value.replace(/\D/g, ''); // 숫자 외의 문자 제거

        if (rawValue.length <= 3) {
            phone.value = rawValue;
        } else if (rawValue.length <= 6) {
            phone.value = rawValue.slice(0, 3) + '-' + rawValue.slice(3);
        } else if (rawValue.length <= 10) {
            phone.value = rawValue.slice(0, 2) + '-' + rawValue.slice(2, 6) + '-' + rawValue.slice(6);
        } else if (rawValue.length <= 11) {
            phone.value = rawValue.slice(0, 3) + '-' + rawValue.slice(3, 7) + '-' + rawValue.slice(7);
        } else {
            phone.value = rawValue.slice(0, 3) + '-' + rawValue.slice(3, 7) + '-' + rawValue.slice(7, 11);
        }
    }

    // 전화번호 유효성 검사
    function isValidPhoneNumber(number) {
        const patterns = /^(010|02|031|051)\d{7,8}$/;
        return patterns.test(number);
    }

    // 전화번호 변경
    function changePhone() {
        phone.value = '';
        phone.style.borderWidth = '1px';
        phone.removeAttribute('readonly');
        phone.setAttribute('maxlength', '13'); // 최대 길이 설정

        document.getElementById('savePhone').style.display = 'inline-block';
        document.getElementById('cancelPhone').style.display = 'inline-block';
        document.getElementById('btnPhone').style.display = 'none';
    }

    // 전화번호 변경 취소
    function cancelPhoneChanges() {
        phone.value = originalPhone;
        phone.style.borderWidth = '0px';
        phone.setAttribute('readonly', true);
        phone.removeAttribute('maxlength'); // 최대 길이 제한 제거

        document.getElementById('savePhone').style.display = 'none';
        document.getElementById('cancelPhone').style.display = 'none';
        document.getElementById('btnPhone').style.display = 'inline-block';
    }

   // 전화번호 저장
    function savePhone(event) {

        const phoneValue = phone.value.replace(/\D/g, ''); // 포맷팅된 문자 제거

        if (!isValidPhoneNumber(phoneValue)) {
        event.preventDefault(); // 기본 폼 제출 방지
            alert('유효하지 않은 전화번호입니다. 올바른 번호를 입력해주세요.');
            return false;
        }

        // 유효한 번호일 때 폼 제출
        document.getElementById('phoneChangeForm').submit();
        alert('전화번호가 성공적으로 변경되었습니다.');
    }

    // 이벤트 리스너 설정
    document.getElementById('btnAddress').addEventListener('click', changeAddress);
    document.getElementById('cancelAddress').addEventListener('click', cancelAddress);
    document.getElementById('saveAddress').addEventListener('click', () => {
        document.getElementById('addressChangeForm').submit();
        alert('주소가 성공적으로 변경되었습니다.');
    });

    document.getElementById('btnPhone').addEventListener('click', changePhone);
    document.getElementById('cancelPhone').addEventListener('click', cancelPhoneChanges);
    document.getElementById('savePhone').addEventListener('click', savePhone); // 수정된 부분
    phone.addEventListener('input', formatPhoneNumber);
});
function showChangeEmailSection() {
    document.getElementById('currentEmail').style.display = 'none';
    document.getElementById('changeEmailSection').style.display = 'block';
    document.getElementById('changeEmailBtn').style.display = 'none';
}

function cancelEmailChange() {
    document.getElementById('currentEmail').style.display = 'inline';
    document.getElementById('changeEmailSection').style.display = 'none';
    document.getElementById('changeEmailBtn').style.display = 'inline-block';
}
