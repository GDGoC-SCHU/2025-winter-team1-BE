document.getElementById('signup-form').addEventListener('submit', (event) => {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('/signup', { // 실제 회원가입 API 엔드포인트로 변경
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: username, password: password })
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/login'; // 회원가입 성공 시 로그인 페이지로 이동
            } else {
                alert('회원가입 실패');
            }
        });
});