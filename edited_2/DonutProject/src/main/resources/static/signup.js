document.getElementById('signup-form').addEventListener('submit', (event) => {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('/user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({ username: username, password: password })
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/login';
            } else {
                return response.text().then(text => {
                    alert('회원가입 실패: ' + text);
                });
            }
        })
        .catch(error => console.error('회원가입 오류:', error));
});