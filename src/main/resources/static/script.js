document.addEventListener('DOMContentLoaded', () => {
    const levelSelect = document.getElementById('level');
    const languageSelect = document.getElementById('language');
    const generateButton = document.getElementById('generate-button');
    const problemSolving = document.getElementById('problem-solving');
    const problemContent = document.getElementById('problem-content');
    const userAnswerInput = document.getElementById('user-answer');
    const submitButton = document.getElementById('submit-button');
    const resultDiv = document.getElementById('result');
    const historyList = document.getElementById('history-list');

    generateButton.addEventListener('click', () => {
        const level = levelSelect.value;
        const language = languageSelect.value;

        fetch('/generate', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ level: level, language: language })
        })
            .then(response => response.json())
            .then(data => {
                problemContent.textContent = data.문제;
                localStorage.setItem('problemId', data.문제); // 문제 내용을 problemId로 사용
                problemSolving.style.display = 'block';
            })
            .catch(error => console.error('문제 생성 오류:', error)); // 오류 처리 추가
    });

    submitButton.addEventListener('click', () => {
        const problemId = localStorage.getItem('problemId');
        const userAnswer = userAnswerInput.value;

        fetch(`/submit?problemId=${encodeURIComponent(problemId)}&userAnswer=${encodeURIComponent(userAnswer)}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        })
            .then(response => response.text()) // 텍스트로 받음
            .then(result => {
                const isCorrect = result === 'true'; // boolean 타입으로 변환
                alert(isCorrect ? '정답입니다!' : '틀렸습니다.'); // 팝업 메시지 표시
                userAnswerInput.value = '';
            })
            .catch(error => console.error('정답 제출 오류:', error));
    });

    fetch('/mypage')
        .then(response => response.text()) // HTML 텍스트로 받음
        .then(html => {
            document.getElementById('problem-list').innerHTML = html; // HTML 내용을 problem-list에 삽입
        })
        .catch(error => console.error('문제 기록 조회 오류:', error));
});